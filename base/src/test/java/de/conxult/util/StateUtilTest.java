/*
 * Copyright by https://conxult.de
 */
package de.conxult.util;

import de.conxult.util.EnumUtil;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author joerg
 */
public class StateUtilTest {

  @Test
  public void shouldEncodeDecodeState() throws Exception {
    String encoded = EnumUtil.toString(SomeStateEnum.STARTED);
    Enum   decoded = EnumUtil.toState(encoded);
    assertEquals(SomeStateEnum.STARTED, decoded);
  }

}
