/*
* Copyright 2022 Basis Technology Corp.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.basistech.rosette.annotations;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import lombok.Builder;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JacksonMixinProcessor extends AbstractProcessor {

    private static final String PACKAGE_NAME = "com.basistech.rosette.apimodel.jackson";
    private static final String VALUE = "value";

    private ProcessingEnvironment processingEnvironment;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        this.processingEnvironment = processingEnvironment;
    }

    @Override
    @SuppressWarnings("java:S3516") // From the Javadoc:  "A processor may always return the same boolean value..."
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        if (roundEnvironment.getElementsAnnotatedWith(JacksonMixin.class).isEmpty()) {
            return true;
        }
        Map<String, String> addMixinCode = new HashMap<>();
        for (Element element : roundEnvironment.getElementsAnnotatedWith(JacksonMixin.class)) {
            if (element.getKind() != ElementKind.CLASS) {
                processingEnvironment.getMessager().printMessage(Diagnostic.Kind.ERROR, "Annotation only available to Class");
                continue;
            }
            TypeElement typeElement = (TypeElement) element;
            String elementQualifiedName = typeElement.getQualifiedName().toString();
            String elementSimpleName = typeElement.getSimpleName().toString();
            if (typeElement.getAnnotation(Builder.class) != null) {
                TypeSpec.Builder mixinClassBuilder = TypeSpec
                        .classBuilder(typeElement.getSimpleName().toString() + "Mixin")
                        .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                        .addAnnotation(AnnotationSpec.builder(JsonTypeName.class)
                                .addMember(VALUE, "$S", typeElement.getSimpleName())
                                .build())
                        .addAnnotation(AnnotationSpec.builder(JsonDeserialize.class)
                                .addMember("builder", CodeBlock.builder()
                                        .add(elementQualifiedName + "." + elementSimpleName + "Builder.class").build())
                                .build())
                        .addAnnotation(AnnotationSpec.builder(JsonInclude.class)
                                .addMember(VALUE, CodeBlock.builder()
                                        .add("JsonInclude.Include.NON_NULL").build())
                                .build())
                        .addType(TypeSpec
                                .classBuilder(typeElement.getSimpleName().toString() + "MixinBuilder")
                                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                                .addAnnotation(AnnotationSpec.builder(JsonPOJOBuilder.class)
                                        .addMember("withPrefix", "$S", "")
                                        .build()).build());
                try {
                    String packageName = elementQualifiedName.substring(0, elementQualifiedName.lastIndexOf("."))
                            + ".jackson";
                    JavaFile.builder(packageName, mixinClassBuilder.build())
                            .build()
                            .writeTo(processingEnvironment.getFiler());
                    addMixinCode.put(elementQualifiedName + ".class",
                            packageName + "." + typeElement.getSimpleName().toString() + "Mixin" + ".class");
                    addMixinCode.put(elementQualifiedName + "." + elementSimpleName + "Builder.class",
                            packageName + "." + typeElement.getSimpleName().toString()
                                    + "Mixin." + typeElement.getSimpleName().toString() + "MixinBuilder.class");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder("addMixins")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .addParameter(Module.SetupContext.class, "context");
        for (String key : addMixinCode.keySet()) {
            methodSpecBuilder.addStatement("        context.setMixInAnnotations($L, $L)", key, addMixinCode.get(key));
        }
        TypeSpec.Builder mixinUtilClassBuilder = TypeSpec
                .classBuilder("MixinUtil")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addAnnotation(AnnotationSpec.builder(SuppressWarnings.class)
                        .addMember(VALUE, "$S", "PMD")
                        .build())
                .addMethod(methodSpecBuilder.build());
        try {
            JavaFile.builder(PACKAGE_NAME, mixinUtilClassBuilder.build())
                    .build()
                    .writeTo(processingEnvironment.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(JacksonMixin.class.getCanonicalName())));
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
