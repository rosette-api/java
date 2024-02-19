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

package com.basistech.rosette.apimodel;

import com.basistech.rosette.apimodel.jackson.ApiModelMixinModule;
import com.basistech.rosette.apimodel.jackson.DocumentRequestMixin;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@SuppressWarnings("PMD.UnusedPrivateMethod") // Parameterized Tests
class ModelTest {
    private ObjectMapper mapper;

    private static Stream<Arguments> packageTestParameters() {
        return Stream.of(
                Arguments.of(true),
                Arguments.of(false)
        );
    }

    @BeforeEach
    public void init() {
        mapper = ApiModelMixinModule.setupObjectMapper(new ObjectMapper());
    }

    @ParameterizedTest(name = "inputStreamContent: {0}")
    @MethodSource("packageTestParameters")
    void packageTest(boolean inputStreams) throws ClassNotFoundException, IOException {
        Reflections reflections = new Reflections(this.getClass().getPackage().getName(), new SubTypesScanner(false));
        Set<Class<?>> allClasses = reflections.getSubTypesOf(Object.class);
        for (Object clazz : allClasses) {
            String className = ((Class) clazz).getName();
            if (className.contains("com.basistech.rosette.dm")) {
                continue;
            }
            if (className.contains("Adm")) {
                continue; // these are too hard.
            }
            if (className.endsWith(".ModelTest")) {
                continue;
            }
            if (className.endsWith(".NonNullTest")) {
                continue;
            }
            if (className.endsWith("Mixin")) {
                continue;
            }

            if (className.endsWith("Builder")) {
                continue;
            }
            if (className.contains(".batch.")) {
                // there are polymorphism issues in here for this test strategy.
                continue;
            }

            if (className.contains("ConfigurationRequest")) {
                continue;
            }
            if (className.contains("RecordSimilarityRequest")) {
                continue;
            }

            Class c = Class.forName(className);
            if (Modifier.isAbstract(c.getModifiers())) {
                continue;
            }
            Constructor[] ctors = c.getDeclaredConstructors();
            if (ctors.length == 0) {
                continue;
            }
            Constructor ctor = ctors[0];
            if (ctor.getParameterTypes().length == 0) {
                continue; // don't want empty constructor
            }
            Object o1;
            if (Modifier.isPublic(ctor.getModifiers())) {

                boolean oldInputStreams = inputStreams;
                try {
                    if (className.endsWith("ConstantsResponse")) {
                        inputStreams = false; // special case due to Object in there.
                    }
                    o1 = createObject(ctor, inputStreams);
                } finally {
                    inputStreams = oldInputStreams;
                }


                // serialize
                // for a request, we might need a view
                ObjectWriter writer = mapper.writerWithView(Object.class);
                if (o1 instanceof DocumentRequest) {
                    DocumentRequest r = (DocumentRequest) o1;
                    if (r.getRawContent() instanceof String || r.getContentUri() != null) {
                        writer = mapper.writerWithView(DocumentRequestMixin.Views.Content.class);
                        // need to get rid of contentType for non-multipart request or it will fail comparison
                        o1 = DocumentRequest.builder()
                                .profileId(r.getProfileId())
                                .language(r.getLanguage())
                                .content(r.getContent())
                                .contentUri(r.getContentUri())
                                .options(r.getOptions())
                                .build();
                    }
                }
                String json = writer.writeValueAsString(o1);
                // deserialize
                Object o2 = mapper.readValue(json, (Class<? extends Object>) clazz);
                // verify
                assertEquals(o1, o2);
            }
        }
    }


    private Object createObject(Constructor ctor, boolean inputStreams) {
        Object o;
        int argSize = ctor.getParameterTypes().length;
        Class[] parameterTypes = ctor.getParameterTypes();
        Object[] args = new Object[argSize];

        for (int i = 0; i < argSize; i++) {
            try {
                args[i] = createObjectForType(parameterTypes[i], ctor.getGenericParameterTypes()[i], inputStreams);
            } catch (Throwable e) {
                e.printStackTrace();
                fail(String.format("Unable to create object %s %d %s %s", ctor, i, parameterTypes[i], ctor.getGenericParameterTypes()[i]));
                return null;
            }

        }
        try {
            o = ctor.newInstance(args);
        } catch (Throwable t) {
            if (!Options.class.equals(ctor.getDeclaringClass())) {
                t.printStackTrace();
                fail(String.format("Unable to create object for %s", ctor));
            }
            return null;
        }
        return o;
    }

    //CHECKSTYLE:OFF
    private Object createObjectForType(Class<?> type, Type genericParameterType, boolean inputStreams)
            throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Object o = null;
        Class firstComponentType = type.isArray() ? type.getComponentType() : type;
        String typeName = firstComponentType.getSimpleName();
        Class parameterArgClass = null;
        Type[] parameterArgTypes = null;
        if (genericParameterType != null) {
            if (genericParameterType instanceof ParameterizedType) {
                ParameterizedType aType = (ParameterizedType) genericParameterType;
                parameterArgTypes = aType.getActualTypeArguments();
                for (Type parameterArgType : parameterArgTypes) {
                    if (parameterArgType instanceof ParameterizedType) {
                        ParameterizedType parameterizedType = (ParameterizedType)parameterArgType;
                        if (isListString(parameterizedType)) {
                            List<List<String>> rv = Lists.newArrayList();
                            rv.add(Lists.newArrayList("string"));
                            return rv;
                        }
                    } else {
                        parameterArgClass = (Class) parameterArgType;
                        if ("Map".equals(typeName)) {
                            break;
                        }
                    }
                }
            }
        }
        if (firstComponentType.isEnum()) {
            return firstComponentType.getEnumConstants()[0];
        }
        switch (typeName) {
        case "byte": {
            if (type.isArray()) {
                o = "somebytes".getBytes();
            } else {
                o = (byte) '8';
            }
            break;
        }
        case "String":
        case "CharSequence": {
            o = "foo";
            break;
        }
        case "long":
        case "Long": {
            o = (long) 123456789;
            break;
        }
        case "Double":
        case "double": {
            o = 1.0;
            break;
        }
        case "int":
        case "Integer": {
            o = 98761234;
            break;
        }
        case "boolean":
        case "Boolean": {
            o = false;
            break;
        }

        case "Collection":
        case "List": {
            if (parameterArgClass != null) {
                Object o1 = createObjectForType(parameterArgClass, null, inputStreams);
                List<Object> list = new ArrayList<>();
                list.add(o1);
                o = list;
            }
            break;
        }
        case "Object":
            if (inputStreams) {
                o = new ByteArrayInputStream(new byte[0]);
            } else {
                o = "foo";
            }
            break;
        case "EnumSet":
            break;
        case "Set": {
            if (parameterArgClass != null) {
                Object o1 = createObjectForType(parameterArgClass, null, inputStreams);
                Set<Object> set = new HashSet<>();
                set.add(o1);
                o = set;
            }
            break;
        }
        case "Map": {
            if (parameterArgTypes != null && parameterArgTypes.length == 2) {
                Class keyClass = (Class) parameterArgTypes[0];
                Object keyObject = createObject(keyClass);
                if (keyObject != null) {
                    HashMap<Object, Object> map = new HashMap<>();
                    map.put(keyObject, null);
                    o = map;
                }
            }
            break;
        }
        case "IAddress": {
            o = new UnfieldedAddress("foo");
            break;
        }
        default:
            if (parameterArgClass != null) {
                Constructor[] ctors = parameterArgClass.getDeclaredConstructors();
                o = createObject(ctors[0], inputStreams);
            } else {
                Constructor[] ctors = firstComponentType.getDeclaredConstructors();
                o = createObject(ctors[0], inputStreams);
            }
        }
        return o;
    }
    //CHECKSTYLE:ON

    private boolean isListString(ParameterizedType parameterizedType) {
        return List.class.equals(parameterizedType.getRawType())
                && parameterizedType.getActualTypeArguments().length == 1
                && String.class.equals(parameterizedType.getActualTypeArguments()[0]);
    }

    private Object createObject(Class clazz) throws IllegalAccessException, InstantiationException,
            InvocationTargetException {
        if (Enum.class.isAssignableFrom(clazz)) {
            // pick a value, any value.
            return clazz.getEnumConstants()[0];
        } else {
            Constructor<?>[] ctors = clazz.newInstance().getClass().getDeclaredConstructors();

            Object o = null;
            for (Constructor ctor : ctors) {
                if (ctor.getGenericParameterTypes().length == 1) {
                    Object objectOfType = createObjectOfType(ctor.getGenericParameterTypes()[0]);
                    if (objectOfType != null) {
                        o = ctor.newInstance(objectOfType);
                        break;
                    }
                }
            }
            return o;
        }
    }

    private Object createObjectOfType(Type type) {
        Object object = null;
        String typeName = ((Class) type).getSimpleName();
        switch (typeName) {
        case "byte[]": {
            object = "abcde".getBytes();
            break;
        }
        case "String": {
            object = "foo";
            break;
        }
        case "StringBuilder": {
            object = new StringBuilder("foo");
            break;
        }
        case "long":
        case "Long": {
            object = (long) 123456789;
            break;
        }
        case "Double":
        case "double": {
            object = 1.0;
            break;
        }
        case "int":
        case "Integer": {
            object = 98761234;
            break;
        }
        case "boolean":
        case "Boolean": {
            object = true;
            break;
        }
        case "Set":
        case "List":
        case "Object":
        case "EnumSet":
            break;
        default:
            break;
        }
        return object;
    }

    @Test
    void doNotDeserializeGenre() throws JsonProcessingException {
        String json = "{\"content\": \"foo\", \"genre\":\"foo\"}";
        // Ensure that the document request no longer deserializes genre
        Request request = mapper.readValue(json, new TypeReference<DocumentRequest<EntitiesOptions>>() { });
        assertTrue(mapper.writeValueAsString(request).indexOf("genre") == -1);
    }
}
