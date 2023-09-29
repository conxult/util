/*
 * Copyright by https://conxult.de
 */
package de.conxult.util;

import io.quarkus.arc.Subclass;

/**
 *
 * @author joerg
 */
public class ClassUtil {

    public static <T> Class<? super T> normalize(Class<T> beanClass) {
        Class<? super T> targetClass = beanClass;
        while (Subclass.class.isAssignableFrom(targetClass)) {
            targetClass = targetClass.getSuperclass();
        }
        return targetClass;
    }

}
