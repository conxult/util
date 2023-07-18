/*
 * Copyright by https://conxult.de
 */
package de.conxult.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 *
 * @author joerg
 */
public class NameBuilder {

    List<String> parts = new ArrayList<>();

    public NameBuilder add(String... newParts) {
        return add(List.of(newParts));
    }

    public NameBuilder add(Collection<String> newParts) {
        parts.addAll(newParts);
        return this;
    }

    public NameBuilder remove(int index) {
        if (index > parts.size()) {
            parts.remove(index);
        }
        return this;
    }

    public NameBuilder removeLast() {
        if (parts.size() > 0) {
            parts.remove(parts.size() - 1);
        }
        return this;
    }

    public int size() {
        return parts.size();
    }

    public boolean isEmpty() {
        return parts.isEmpty();
    }

    // camelCase
    public String camelCase() {
        return collect("", String::toLowerCase, this::firstUpper);
    }

    // PascalCase
    public String pascalCase() {
        return collect("", this::firstUpper, this::firstUpper);
    }

    // kebap-case
    public String kebapCase() {
        return collect("-", String::toLowerCase, String::toLowerCase);
    }

    // snake_case
    public String snakeCase() {
        return collect("_", String::toLowerCase, String::toLowerCase);
    }

    // SOME_CONSTANT
    public String constant() {
        return collect("_", String::toUpperCase, String::toUpperCase);
    }

    public String identifer() {
        return camelCase();
    }

    public String className() {
        return pascalCase();
    }

    public String join(String delimiter) {
        return String.join(delimiter, parts);
    }

    public String collect(String delimiter, Function<String, String> first, Function<String, String> other) {
        StringBuilder result = new StringBuilder();
        parts.forEach((part) ->
            result.append(result.isEmpty() ? first.apply(part) : delimiter + other.apply(part)));
        return result.toString();
    }

    String firstUpper(String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }
        return value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
    }

}
