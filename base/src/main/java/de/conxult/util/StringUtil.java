/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.conxult.util;

/**
 *
 * @author joerg
 */
public class StringUtil {

    // camelCase
    static public String toCamelCase(String source) {
        boolean isKebabCase = source.contains("-");
        boolean isSnakeCase = source.contains("_");

        StringBuilder result = new StringBuilder();
        boolean forceUpperCase = false;

        for (char c : source.toCharArray()) {
            if (c == '-' || c == '_') {
                forceUpperCase = true;
            } else if (forceUpperCase) {
                result.append(Character.toUpperCase(c));
                forceUpperCase = false;
            } else if (isKebabCase || isSnakeCase || result.length() == 0) {
                result.append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }

    // PascalCase
    static public String toPascalCase(String source) {
        String camelCase = toCamelCase(source);
        return camelCase.isEmpty() ? source : camelCase.substring(0, 1).toUpperCase() + camelCase.substring(1);
    }

    // snake_case
    static public String toSnakeCase(String source) {
        if (source.contains("-")) {
            // kebap-case
            return source.replace('-', '_').toLowerCase();
        }
        if (source.contains("_")) {
            // snake_case
            return source.toLowerCase();
        }

        StringBuilder result = new StringBuilder();

        for (char c : source.toCharArray()) {
            if (Character.isUpperCase(c) && result.length() != 0) {
                result.append("_");
            }
            result.append(Character.toLowerCase(c));
        }

        return result.toString();
    }

    // CONST_CASE
    static public String toConstCase(String source) {
        return toSnakeCase(source).toUpperCase();
    }

    // kebab-case
    static public String toKebabCase(String source) {
        return toSnakeCase(source).replace('_', '-');
    }

    static public String firstUpper(String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }
        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }

    static public String firstLower(String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }
        return value.substring(0, 1).toLowerCase() + value.substring(1);
    }

}
