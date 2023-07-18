/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.conxult.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

/**
 *
 * @author joerg
 */
public class ClassCacheTest {

  ClassCache testee = ClassCache.instanceOf(Sample.class);

  @Test
  public void shouldCollectAllFields() {
    ArrayList<Field> fields = testee.getFields();
    assertEquals(5, fields.size());
    assertEquals("someString", fields.get(0).getName());
    assertEquals("someString1", fields.get(1).getName());
    assertEquals("someString12", fields.get(2).getName());
    assertEquals("someString123", fields.get(3).getName());
    assertEquals("someString2", fields.get(4).getName());
  }

  @Test
  public void shouldCollectAllFieldsAnd() {
    ArrayList<Field> fields = testee.getFields().and(SomeAnnotation1.class);
    assertEquals(3, fields.size());
    assertEquals("someString1", fields.get(0).getName());
    assertEquals("someString12", fields.get(1).getName());
    assertEquals("someString123", fields.get(2).getName());
  }

  @Test
  public void shouldCollectOrFields() {
    ArrayList<Field> fields = testee.getFields(SomeAnnotation1.class, SomeAnnotation1.class);
    assertEquals(3, fields.size());
    assertEquals("someString1", fields.get(0).getName());
    assertEquals("someString12", fields.get(1).getName());
    assertEquals("someString123", fields.get(2).getName());
  }

  @Test
  public void shouldCollectSomeFieldsAnd() {
    ArrayList<Field> fields = testee.getFields(SomeAnnotation1.class).and(SomeAnnotation2.class);
    assertEquals(2, fields.size());
    assertEquals("someString12", fields.get(0).getName());
    assertEquals("someString123", fields.get(1).getName());
  }

  @Test
  public void shouldCollectFieldsStartWith() {
    ArrayList<Field> fields = testee.getFields("someString1*");
    assertEquals(3, fields.size());
    assertEquals("someString1", fields.get(0).getName());
    assertEquals("someString12", fields.get(1).getName());
    assertEquals("someString123", fields.get(2).getName());
  }

  @Test
  public void shouldCollectNameFields() {
    ArrayList<Field> fields = testee.getFields("someString1");
    assertEquals(1, fields.size());
    assertEquals("someString1", fields.get(0).getName());
  }

  @Test
  public void shouldGetAllAnnotations() {
    ArrayList<Field> fields = testee.getFields("someString12");
    assertEquals(1, fields.size());
    assertAnnotations(testee.getAnnotations(fields.get(0)), "1@someString12", "2@someString12");
  }

  @Test
  public void shouldGetSpecificAnnotation() {
    ArrayList<Field> fields = testee.getFields("someString123");
    assertEquals(testee.getAnnotation(fields.get(0), SomeAnnotation1.class).value(), "1@someString123");
  }

  @Test
  public void shouldGetSpecificAnnotations() {
    ArrayList<Field> fields = testee.getFields("someString123");
    assertAnnotations(testee.getAnnotations(fields.get(0), SomeAnnotation1.class, SomeAnnotation3.class), "1@someString123", "3@someString123");
  }

  @Test
  public void shouldNotGetSpecificAnnotation() {
    ArrayList<Field> fields = testee.getFields("someString1");
    assertEquals(1, fields.size());
    assertNull(testee.getAnnotation(fields.get(0), SomeAnnotation2.class));
  }

  void assertAnnotations(ClassCache.AnnotationList annotations, String... values) {
    assertEquals(values.length, annotations.size());
    Collections.sort(annotations, (a1, a2) -> a1.toString().compareTo(a2.toString()));
    for (int a = 0; (a < values.length); a++) {
      if (annotations.get(a) instanceof SomeAnnotation1) {
        assertEquals(values[a], ((SomeAnnotation1)annotations.get(a)).value());
      } else if (annotations.get(a) instanceof SomeAnnotation2) {
        assertEquals(values[a], ((SomeAnnotation2)annotations.get(a)).value());
      } else if (annotations.get(a) instanceof SomeAnnotation3) {
        assertEquals(values[a], ((SomeAnnotation3)annotations.get(a)).value());
      } else {
        fail("unexpected annotation " + annotations.get(a));
      }
    }
  }

  static class Sample {
    String someString;

    @SomeAnnotation1("1@someString1")
    String someString1;

    @SomeAnnotation2("2@someString2")
    String someString2;

    @SomeAnnotation1("1@someString12")
    @SomeAnnotation2("2@someString12")
    String someString12;

    @SomeAnnotation1("1@someString123")
    @SomeAnnotation2("2@someString123")
    @SomeAnnotation3("3@someString123")
    String someString123;

  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target({ ElementType.FIELD, ElementType.METHOD })
  public @interface SomeAnnotation1 {
    String value();
  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target({ ElementType.FIELD, ElementType.METHOD })
  public @interface SomeAnnotation2 {
    String value();
  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target({ ElementType.FIELD, ElementType.METHOD })
  public @interface SomeAnnotation3 {
    String value();
  }

}
