/*
 * Copyright by https://conxult.de
 */
package de.conxult.util;

/**
 *
 * @author joerg
 */
public class EnumUtil {

  public static final String DELIMITER = ":";

  public static String toString(Enum state) {
    return state.getDeclaringClass().getName() + DELIMITER + state.name();
  }

  public static Enum toState(String state) {
    try {
      String[] stateSplit = state.split(DELIMITER);
      return (Enum)Class.forName(stateSplit[0])
        .getMethod("valueOf", String.class)
        .invoke(null, stateSplit[1]);
    } catch (Exception any) {
      return null;
    }
  }

}
