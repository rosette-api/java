/*
* Copyright 2026 Basis Technology Corp.
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

import com.google.testing.compile.Compilation;
import com.google.testing.compile.Compiler;
import com.google.testing.compile.JavaFileObjects;
import org.junit.jupiter.api.Test;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.util.List;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test for {@link JacksonMixinProcessor} to verify:
 * 1. Proper error messages when code generation fails (instead of printStackTrace)
 * 2. Warning messages when JacksonMixin is used without Builder
 */
public class JacksonMixinProcessorTest {

    @Test
    public void testJacksonMixinWithoutBuilderProducesWarning() {
        JavaFileObject classWithoutBuilder = JavaFileObjects.forSourceString(
                "test.ModelWithoutBuilder",
                "package test;\n"
                + "\n"
                + "import com.basistech.rosette.annotations.JacksonMixin;\n"
                + "import lombok.Value;\n"
                + "\n"
                + "@Value\n"
                + "@JacksonMixin\n"
                + "public class ModelWithoutBuilder {\n"
                + "    String name;\n"
                + "}\n"
        );

        Compilation compilation = Compiler.javac()
                .withProcessors(new JacksonMixinProcessor())
                .compile(classWithoutBuilder);

        // Compilation should succeed (it's a warning, not an error)
        assertThat(compilation).succeeded();

        List<Diagnostic<? extends JavaFileObject>> warnings = compilation.warnings();
        String warningMessage = warnings.get(0).getMessage(null);
        assertTrue(warningMessage.contains("@JacksonMixin requires @Builder annotation"),
                "Warning should state that Builder annotation is required. Actual: " + warningMessage);
    }

    @Test
    public void testClassWithoutJacksonMixinHasNoWarnings() {
        JavaFileObject normalClass = JavaFileObjects.forSourceString(
                "test.NormalClass",
                "package test;\n"
                + "\n"
                + "public class NormalClass {\n"
                + "    String name;\n"
                + "}\n"
        );

        Compilation compilation = Compiler.javac()
                .withProcessors(new JacksonMixinProcessor())
                .compile(normalClass);

        assertThat(compilation).succeeded();

        // Should have no warnings
        List<Diagnostic<? extends JavaFileObject>> warnings = compilation.warnings();
        assertEquals(0, warnings.size(),
                "Should have no warnings for classes without JacksonMixin");
    }


    @Test
    public void testJacksonMixinOnInterfaceProducesError() {
        JavaFileObject interfaceWithAnnotation = JavaFileObjects.forSourceString(
                "test.MyInterface",
                "package test;\n"
                + "\n"
                + "import com.basistech.rosette.annotations.JacksonMixin;\n"
                + "\n"
                + "@JacksonMixin\n"
                + "public interface MyInterface {\n"
                + "    String getName();\n"
                + "}\n"
        );

        Compilation compilation = Compiler.javac()
                .withProcessors(new JacksonMixinProcessor())
                .compile(interfaceWithAnnotation);

        // Should fail with an error
        assertThat(compilation).failed();

        // Verify it's a meaningful error message (not a stack trace)
        List<Diagnostic<? extends JavaFileObject>> errors = compilation.errors();
        String errorMessage = errors.get(0).getMessage(null);
        assertTrue(errorMessage.contains("Annotation only available to Class"),
                "Error should state annotation is only for classes. Actual: " + errorMessage);
    }

    @Test
    public void testJacksonMixinOnEnumProducesError() {
        JavaFileObject enumWithAnnotation = JavaFileObjects.forSourceString(
                "test.MyEnum",
                "package test;\n"
                + "\n"
                + "import com.basistech.rosette.annotations.JacksonMixin;\n"
                + "\n"
                + "@JacksonMixin\n"
                + "public enum MyEnum {\n"
                + "    VALUE1, VALUE2\n"
                + "}\n"
        );

        Compilation compilation = Compiler.javac()
                .withProcessors(new JacksonMixinProcessor())
                .compile(enumWithAnnotation);

        // Should fail with an error
        assertThat(compilation).failed();

        // Verify it's a meaningful error message (not a stack trace)
        List<Diagnostic<? extends JavaFileObject>> errors = compilation.errors();
        String errorMessage = errors.get(0).getMessage(null);
        assertTrue(errorMessage.contains("Annotation only available to Class"),
                "Error should state annotation is only for classes. Actual: " + errorMessage);
    }

    @Test
    public void testGetSupportedAnnotationTypes() {
        JacksonMixinProcessor processor = new JacksonMixinProcessor();
        java.util.Set<String> supportedTypes = processor.getSupportedAnnotationTypes();

        // Should have exactly one supported annotation type
        assertEquals(1, supportedTypes.size(),
                "Should support exactly one annotation type");

        // Should be the JacksonMixin annotation
        String expectedType = JacksonMixin.class.getCanonicalName();
        assertTrue(supportedTypes.contains(expectedType),
                "Should support " + expectedType + " annotation. Actual: " + supportedTypes);
    }
}
