/******************************************************************************
 ** Copyright (c) 2014-2015 Basis Technology Corporation.
 **
 ** Licensed under the Apache License, Version 2.0 (the "License");
 ** you may not use this file except in compliance with the License.
 ** You may obtain a copy of the License at
 **
 **     http://www.apache.org/licenses/LICENSE-2.0
 **
 ** Unless required by applicable law or agreed to in writing, software
 ** distributed under the License is distributed on an "AS IS" BASIS,
 ** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 ** See the License for the specific language governing permissions and
 ** limitations under the License.
 ******************************************************************************/

package com.basistech.rosette.apimodel;

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

import org.junit.Before;
import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.basistech.rosette.apimodel.jackson.ApiModelMixinModule;

import static org.junit.Assert.assertEquals;

public class ModelTest {

    ObjectMapper mapper;
    
    @Before 
    public void init() {
        mapper = ApiModelMixinModule.setupObjectMapper(new ObjectMapper());
    }

    @Test
    public void packageTest() throws ClassNotFoundException, IllegalAccessException, InvocationTargetException,
            InstantiationException, IOException {
        Reflections reflections = new Reflections(this.getClass().getPackage().getName(), new SubTypesScanner(false));

        Set<Class<? extends Object>> allClasses = reflections.getSubTypesOf(Object.class);
        for (Object clazz : allClasses) {
            String className = ((Class) clazz).getName();
            if (className.endsWith(".ModelTest")) {
                continue;
            }
            if (className.endsWith(".NonNullTest")) {
                continue;
            }
            if (className.endsWith("Mixin")) {
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
                o1 = createObject(ctor);
                // serialize
                byte[] bytes = mapper.writeValueAsBytes(o1);
                // deserialize
                Object o2 = mapper.readValue(bytes, (Class<? extends Object>) clazz);
                // verify
                assertEquals(o1, o2);
            }
        }
    }

    private Object createObject(Constructor ctor) throws IllegalAccessException, InvocationTargetException,
            InstantiationException {
        Object o;
        int argSize = ctor.getParameterTypes().length;
        Class[] parameterTypes = ctor.getParameterTypes();
        Object[] args = new Object[argSize];
        for (int i = 0; i < argSize; i++) {
            args[i] = createObjectForType(parameterTypes[i], ctor.getGenericParameterTypes()[i]);
        }
        o = ctor.newInstance(args);
        return o;
    }

    private Object createObjectForType(Class type, Type genericParameterType) throws IllegalAccessException,
            InstantiationException, InvocationTargetException {
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
                    parameterArgClass = (Class) parameterArgType;
                    if ("Map".equals(typeName)) {
                        break;
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
            case "String": {
                o = "foo";
                break;
            }
            case "long":
            case "Long": {
                o = (long) 123456789;
                break;
            }
            case "Double":
            case "double" : {
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
            case "List": {
                if (parameterArgClass != null) {
                    Object o1 = createObjectForType(parameterArgClass, null);
                    List<Object> list = new ArrayList<>();
                    list.add(o1);
                    o = list;
                }
                break;
            }
            case "Object":
            case "EnumSet":
                break;
            case "Set": {
                if (parameterArgClass != null) {
                    Object o1 = createObjectForType(parameterArgClass, null);
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
            default:
                if (parameterArgClass != null) {
                    Constructor[] ctors = parameterArgClass.getDeclaredConstructors();
                    o = createObject(ctors[0]);
                } else {
                    Constructor[] ctors = firstComponentType.getDeclaredConstructors();
                    o = createObject(ctors[0]);
                }
        }
        return o;
    }

    private Object createObject(Class clazz) throws IllegalAccessException, InstantiationException,
            InvocationTargetException {
        Constructor<?>[] ctors = clazz.newInstance().getClass().getDeclaredConstructors();
        Object o = null;
        for (Constructor ctor : ctors) {
            if (ctor.getGenericParameterTypes().length == 1) {
                o = ctor.newInstance(createObjectOfType(ctor.getGenericParameterTypes()[0]));
                break;
            }
        }
        return o;
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
}
