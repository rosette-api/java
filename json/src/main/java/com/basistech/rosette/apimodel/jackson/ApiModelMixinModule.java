/*
* Copyright 2017 Basis Technology Corp.
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

package com.basistech.rosette.apimodel.jackson;

import com.basistech.rosette.RosetteRuntimeException;
import com.basistech.rosette.apimodel.AccuracyMode;
import com.basistech.rosette.apimodel.Request;
import com.basistech.rosette.apimodel.SentimentModelType;
import com.basistech.rosette.dm.jackson.AnnotatedDataModelModule;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.module.SimpleSerializers;
import com.google.common.reflect.ClassPath;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.annotation.AnnotationDescription;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Jackson module to configure Json serialization and deserialization for the
 * Rosette API model.
 */
@SuppressWarnings("deprecation")
public class ApiModelMixinModule extends AnnotatedDataModelModule {

    public ApiModelMixinModule() {
        super();
    }

    private Set<Class<?>> getModelClasses() {
        String modelPackage = Request.class.getPackage().getName();  // com.basistech.rosette.apimodel
        ClassLoader classLoader = getClass().getClassLoader();
        Set<Class<?>> modelClasses = new HashSet<>();
        try {
            ClassPath classPath = ClassPath.from(classLoader);
            for (ClassPath.ClassInfo classInfo : classPath.getTopLevelClassesRecursive(modelPackage)) {
                if (!classInfo.getPackageName().endsWith(".jackson")) {
                    modelClasses.add(classInfo.load());
                }
            }
        } catch (IOException e) {
            throw new RosetteRuntimeException("Unable to locate model classes", e);
        }
        return modelClasses;
    }

    public void setupModule(Module.SetupContext context) {

        Set<Class<?>> modelClasses = getModelClasses();

        super.setupModule(context);
        for (Class modelClass : modelClasses) {
            // lombok @Builder annotated model class should contain at least one inner class, the builder
            if (modelClass.getDeclaredClasses().length == 0) {
                continue;
            }
            // Some more complex JsonView handling is best left in an actual mixin file
            // Also to work around https://github.com/rzwitserloot/lombok/issues/853
            if (Request.class.isAssignableFrom(modelClass)) {
                try {
                    Class mixinClass = Class.forName(modelClass.getPackage().getName() + ".jackson." +  modelClass.getSimpleName() + "Mixin");
                    Class builderClass = Class.forName(modelClass.getCanonicalName() + "$" + modelClass.getSimpleName() + "Builder");
                    Class builderMixinClass = Class.forName(mixinClass.getCanonicalName() + "$" + modelClass.getSimpleName() + "BuilderMixin");
                    context.setMixInAnnotations(modelClass, mixinClass);
                    context.setMixInAnnotations(builderClass, builderMixinClass);
                } catch (ClassNotFoundException e) {
                    //
                }
                continue;
            }
            Class innerBuilderClass = null;
            for (Class clazz: modelClass.getDeclaredClasses()) {
                // make sure for model class Xyz, there's the corresponding builder inner class XyzBuilder
                if (clazz.getSimpleName().equals(modelClass.getSimpleName() + "Builder")) {
                    innerBuilderClass = clazz;
                    break;
                }
            }
            // only register mixin when there's a builder class
            // this block replaces many XyzMixin.java files
            if (innerBuilderClass != null) {
                try {
                    // create mixins on-the-fly
                    Class<?> modelMixinType = new ByteBuddy()
                            .subclass(Object.class)
                            .name(modelClass.getCanonicalName() + "Mixin")
                            .annotateType(AnnotationDescription.Builder.ofType(JsonTypeName.class)
                                    .define("value", modelClass.getSimpleName())
                                    .build())
                            .annotateType(AnnotationDescription.Builder.ofType(JsonDeserialize.class)
                                    .define("builder", innerBuilderClass)
                                    .build())
                            .annotateType(AnnotationDescription.Builder.ofType(JsonInclude.class)
                                    .define("value", JsonInclude.Include.NON_NULL)
                                    .build())
                            .make()
                            .load(getClass().getClassLoader())
                            .getLoaded();
                    Class<?> modelBuilderMixinType = new ByteBuddy()
                            .subclass(Object.class)
                            .name(modelClass.getCanonicalName() + "BuilderMixin")
                            .annotateType(AnnotationDescription.Builder.ofType(JsonPOJOBuilder.class)
                                    // Jackson defaults to 'withXyz' for the builder fluent, so we need to
                                    // get rid of the prefix to accommodate lombok @Builder default which has
                                    // no prefix
                                    .define("withPrefix", "")
                                    .build())
                            .make()
                            .load(getClass().getClassLoader())
                            .getLoaded();
                    context.setMixInAnnotations(modelClass, modelMixinType);
                    context.setMixInAnnotations(innerBuilderClass, modelBuilderMixinType);
                } catch (IllegalStateException e) {
                    // loaded already, skipping
                }
            }
        }

        // TODO: see if there's something similar that can be used to generalize enum handling
        context.setMixInAnnotations(AccuracyMode.class, AccuracyModeMixin.class);
        SimpleSerializers keySerializers = new SimpleSerializers();
        keySerializers.addSerializer(new AccuracyModeSerializer());
        context.addKeySerializers(keySerializers);

        context.setMixInAnnotations(SentimentModelType.class, SentimentModelTypeMixin.class);
        keySerializers = new SimpleSerializers();
        keySerializers.addSerializer(new SentimentModelTypeSerializer());
        context.addKeySerializers(keySerializers);
    }

    /**
     * Register the Rosette API Model Jackson module on an {@link ObjectMapper}.
     *
     * @param mapper the mapper.
     * @return the same mapper, for convenience.
     */
    public static ObjectMapper setupObjectMapper(ObjectMapper mapper) {
        final ApiModelMixinModule module = new ApiModelMixinModule();
        mapper.registerModule(module);
        return mapper;
    }
}
