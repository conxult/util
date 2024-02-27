/*
 * Copyright by https://conxult.de
 */
package de.conxult.util;

import org.junit.jupiter.api.Test;

/**
 *
 * @author joerg
 */
public class TimeUtilTest {

    @Test
    public void shouldParse_YYYYMMDD_HHMMSSsssZ() {
        OffsetDateTimeUtil.of("1966-04-23T13:14:15.789Z");
    }

    @Test
    public void shouldParse_YYYYMMDD_HHMMSSsss01() {
        OffsetDateTimeUtil.of("1966-04-23T13:14:15.789+01");
    }

    @Test
    public void shouldParse_YYYYMMDD_HHMMSSsss() {
        OffsetDateTimeUtil.of("1966-04-23 13:14:15.789");
    }

    @Test
    public void shouldParse_YYYYMMDD_HHMMSS() {
        OffsetDateTimeUtil.of("1966-04-23 13:14:15");
    }

    @Test
    public void shouldParse_YYYYMMDD_HHMM() {
        OffsetDateTimeUtil.of("1966-04-23 13:14");
    }

    @Test
    public void shouldParse_YYYYMMDD_HHMMSS_Slash() {
        OffsetDateTimeUtil.of("1966/04/23 13:14:15");
    }

    @Test
    public void shouldParse_YYYYMMDD_HHMM_Slash() {
        OffsetDateTimeUtil.of("1966/04/23 13:14");
    }

    @Test
    public void shouldParse_DDMMYYYY_HHMMSSsss() {
        OffsetDateTimeUtil.of("23.04.1966 13:14:15.789");
    }

    @Test
    public void shouldParse_DDMMYYYY_HHMMSS() {
        OffsetDateTimeUtil.of("23.04.1966 13:14:15");
    }

    @Test
    public void shouldParse_DDMMYYYY_HHMM() {
        OffsetDateTimeUtil.of("23.04.1966 13:14");
    }

    @Test
    public void shouldParse_DDMMYY_HHMMSSsss() {
        OffsetDateTimeUtil.of("23.04.66 13:14:15.789");
    }

    @Test
    public void shouldParse_DDMMYY_HHMMSS() {
        OffsetDateTimeUtil.of("23.04.66 13:14:15");
    }

    @Test
    public void shouldParse_DDMMYY_HHMM() {
        OffsetDateTimeUtil.of("23.04.66 13:14");
    }
}
