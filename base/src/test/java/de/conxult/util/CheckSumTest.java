/*
 * Copyright by https://conxult.de
 */
package de.conxult.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 *
 * @author joerg
 */
public class CheckSumTest {

  @Test
  public void shouldConvertToHex() throws Exception {
    assertEquals("01020304", CheckSum.toHex(new byte[] { 1,2,3,4 }));
    assertEquals("1020407fff80", CheckSum.toHex(new byte[] { 16, 32, 64, 127, -1, -128 }));
  }

}
