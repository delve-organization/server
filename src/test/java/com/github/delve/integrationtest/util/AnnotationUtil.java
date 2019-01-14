package com.github.delve.integrationtest.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class AnnotationUtil {

    private AnnotationUtil() {
    }

    public static <T extends Annotation> T findAnnotationOnMethod(final Method method, final Class<T> annotationType) {
        final Annotation[] methodAnnotations = method.getDeclaredAnnotations();
        for (Annotation annotation : methodAnnotations) {
            if (annotationType.equals(annotation.annotationType())) {
                return (T) annotation;
            }
        }
        return null;
    }
}
