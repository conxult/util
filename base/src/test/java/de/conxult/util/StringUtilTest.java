/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.conxult.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author joerg
 */
public class StringUtilTest {

  @Test
  public void shouldConvertToCamelCase() {
    assertEquals("camelCase", StringUtil.toCamelCase("camelCase"));
    assertEquals("camelCase", StringUtil.toCamelCase("CamelCase"));
    assertEquals("camelCase", StringUtil.toCamelCase("CAMEL_CASE"));
    assertEquals("camelCase", StringUtil.toCamelCase("CaMeL_CaSe"));
    assertEquals("camelCase", StringUtil.toCamelCase("camel-case"));
    assertEquals("camelCase", StringUtil.toCamelCase("cAmEl-cAsE"));
  }

  @Test
  public void shouldConvertToPascalCase() {
    assertEquals("PascalCase", StringUtil.toPascalCase("pascalCase"));
    assertEquals("PascalCase", StringUtil.toPascalCase("PascalCase"));
    assertEquals("PascalCase", StringUtil.toPascalCase("PASCAL_CASE"));
    assertEquals("PascalCase", StringUtil.toPascalCase("PaScAl_CaSe"));
    assertEquals("PascalCase", StringUtil.toPascalCase("pascal-case"));
    assertEquals("PascalCase", StringUtil.toPascalCase("pAsCaL-cAsE"));
  }

  @Test
  public void shouldConvertToSnakeCase() {
    assertEquals("snake_case", StringUtil.toSnakeCase("snakeCase"));
    assertEquals("snake_case", StringUtil.toSnakeCase("SnakeCase"));
    assertEquals("snake_case", StringUtil.toSnakeCase("snake_case"));
    assertEquals("snake_case", StringUtil.toSnakeCase("sNaKe_cAsE"));
    assertEquals("snake_case", StringUtil.toSnakeCase("SNAKE_CASE"));
    assertEquals("snake_case", StringUtil.toSnakeCase("snake-case"));
    assertEquals("snake_case", StringUtil.toSnakeCase("sNaKe-cAsE"));
    assertEquals("snake_case", StringUtil.toSnakeCase("SNAKE-CASE"));
  }

  @Test
  public void shouldConvertToConstCase() {
    assertEquals("CONST_CASE", StringUtil.toConstCase("constCase"));
    assertEquals("CONST_CASE", StringUtil.toConstCase("ConstCase"));
    assertEquals("CONST_CASE", StringUtil.toConstCase("const_case"));
    assertEquals("CONST_CASE", StringUtil.toConstCase("cOnSt_cAsE"));
    assertEquals("CONST_CASE", StringUtil.toConstCase("CONST_CASE"));
    assertEquals("CONST_CASE", StringUtil.toConstCase("const-case"));
    assertEquals("CONST_CASE", StringUtil.toConstCase("cOnSt-cAsE"));
    assertEquals("CONST_CASE", StringUtil.toConstCase("CONST-CASE"));
  }

  @Test
  public void shouldConvertToKebapCase() {
    assertEquals("kebap-case", StringUtil.toKebabCase("kebapCase"));
    assertEquals("kebap-case", StringUtil.toKebabCase("KebapCase"));
    assertEquals("kebap-case", StringUtil.toKebabCase("kebap_case"));
    assertEquals("kebap-case", StringUtil.toKebabCase("kEbAp_cAsE"));
    assertEquals("kebap-case", StringUtil.toKebabCase("KEBAP_CASE"));
    assertEquals("kebap-case", StringUtil.toKebabCase("kebap-case"));
    assertEquals("kebap-case", StringUtil.toKebabCase("kEbAp-cAsE"));
    assertEquals("kebap-case", StringUtil.toKebabCase("KEBAP-CASE"));
  }

}
