/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.conxult.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author joerg
 */
public class ClassCache {

  private static final Map<Class<?>, ClassCache> classCaches = new ConcurrentHashMap<Class<?>, ClassCache>();

  private MemberList<Field>           fields      = new MemberList<>();
  private MemberList<Method>          methods     = new MemberList<>();
  private Map<Member, AnnotationList> annotations = new HashMap<>();

  ClassCache(Class<?> clazz) {
    if (clazz.getSuperclass() != null &&
        !clazz.getName().startsWith("java.") &&
        !clazz.getName().startsWith("javax.")) {
      ClassCache superClassCache = ClassCache.instanceOf(clazz.getSuperclass());
      fields.addAll(superClassCache.fields);
      methods.addAll(superClassCache.methods);
      annotations.putAll(superClassCache.annotations);
    }

    for (Field field : clazz.getDeclaredFields()) {
      try {
        field.setAccessible(true);
      } catch (Exception any) {
      }
      fields.add(field);
      annotations.put(field, new AnnotationList(Arrays.asList(field.getAnnotations())));
    }

    for (Method method : clazz.getDeclaredMethods()) {
      if (!method.getDeclaringClass().equals(Object.class)) {
        try {
          method.setAccessible(true);
        } catch (Exception any) {
        }
        methods.add(method);
        annotations.put(method, new AnnotationList(Arrays.asList(method.getAnnotations())));
      }
    }

    Collections.sort(fields, (f1, f2) -> f1.getName().compareTo(f2.getName()));
    Collections.sort(methods, (m1, m2) -> m1.getName().compareTo(m2.getName()));
  }

  public Field getField(String pattern, Class<? extends Annotation>... annotationClasses) {
    return getFields(pattern, annotationClasses).getSingleResult();
  }

  public MemberList<Field> getFields(String pattern, Class<? extends Annotation>... annotationClasses) {
    return fields.name(pattern).or(annotationClasses);
  }

  public MemberList<Field> getFields(Class<? extends Annotation>... annotationClasses) {
    return fields.or(annotationClasses);
  }

  public MemberList<Method> getMethod(String pattern, Class<? extends Annotation>... annotationClasses) {
    return methods.name(pattern).or(annotationClasses);
  }

  public MemberList<Method> getMethods(String pattern, Class<? extends Annotation>... annotationClasses) {
    return methods.name(pattern).or(annotationClasses);
  }

  public MemberList<Method> getMethods(Class<? extends Annotation>... annotationClasses) {
    return methods.or(annotationClasses);
  }

  public AnnotationList getAnnotations(Member member, Class<? extends Annotation>... annotationClasses) {
    return annotations.computeIfAbsent(member, m -> new AnnotationList())
      .or(annotationClasses);
  }

  public <A extends Annotation> A getAnnotation(Member member, Class<A> annotationClass) {
    AnnotationList result = annotations.computeIfAbsent(member, m -> new AnnotationList())
      .or(annotationClass);
    return result.isEmpty() ? null : (A)result.get(0);
  }

  public static ClassCache instanceOf(Class<?> clazz) {
    ClassCache classCache = classCaches.get(clazz);
    if (classCache == null) {
      classCache = new ClassCache(clazz);
      classCaches.put(clazz, classCache);
    }
    return classCache;
  }

  public static class MemberList<T extends AccessibleObject & Member>
    extends ArrayList<T> {

    MemberList() {
    }

    MemberList<T> name(String pattern) {
      MemberList<T> result = this;

      if (pattern != null && !pattern.isEmpty() && !pattern.equals("*")) {
        result = new MemberList();
        if (pattern.endsWith("*")) {
          pattern = pattern.substring(0, pattern.length() -1);
          for (T t : this) {
            if (t.getName().startsWith(pattern)) {
              result.add(t);
            }
          }
        } else {
          for (T t : this) {
            if (t.getName().equals(pattern)) {
              result.add(t);
            }
          }
        }
      }

      return result;
    }

    public T getSingleResult() {
      return (size() == 1) ? get(0) : null;
    }

    MemberList<T> or(Class<? extends Annotation>... annotationClasses) {
      MemberList<T> result = this;

      if (annotationClasses.length > 0) {
        result = new MemberList();
        stream().filter(m -> hasAnyAnnotation(m, annotationClasses)).forEach(result::add);
      }

      return result;
    }

    public MemberList<T> and(Class<? extends Annotation>... annotationClasses) {
      MemberList<T> result = this;

      if (annotationClasses.length > 0) {
        result = new MemberList();
        stream().filter(m -> hasAllAnnotations(m, annotationClasses)).forEach(result::add);
      }

      return result;
    }

    static <T extends AccessibleObject> boolean hasAnyAnnotation(T t, Class<? extends Annotation>... annotationClasses) {
      for (Class<? extends Annotation> annotationClass : annotationClasses) {
        if (t.isAnnotationPresent(annotationClass)) {
          return true;
        }
      }
      return false;
    }

    static <T extends AccessibleObject> boolean hasAllAnnotations(T t, Class<? extends Annotation>... annotationClasses) {
      for (Class<? extends Annotation> annotationClass : annotationClasses) {
        if (!t.isAnnotationPresent(annotationClass)) {
          return false;
        }
      }
      return true;
    }

  }

  public static class AnnotationList
    extends ArrayList<Annotation> {

    public AnnotationList() {
    }

    public AnnotationList(List<Annotation> annotations) {
      addAll(annotations);
    }

    AnnotationList or(Class<? extends Annotation>... annotationClasses) {
      AnnotationList result = this;
      if (annotationClasses.length > 0) {
        result = new AnnotationList();
        for (Annotation a : this) {
          for (Class<? extends Annotation> annotationClass : annotationClasses) {
            if (a.annotationType().equals(annotationClass)) {
              result.add(a);
              break;
            }
          }
        }
      }
      return result;
    }



  }


}
