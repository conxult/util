/*
 * Copyright by https://conxult.de
 */
package de.conxult.util;

import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author joerg
 */
@Target(value = {TYPE, CONSTRUCTOR, FIELD, METHOD, PARAMETER, LOCAL_VARIABLE})
@Retention(value = RetentionPolicy.SOURCE)
@Repeatable(ToDo.ToDos.class)
@Documented
public @interface ToDo {
    String value() default "";

    @Target(value = {TYPE, CONSTRUCTOR, FIELD, METHOD, PARAMETER, LOCAL_VARIABLE})
    @Retention(value = RetentionPolicy.SOURCE)
    @Documented
    public @interface ToDos {
        ToDo[] value();
    }

}
