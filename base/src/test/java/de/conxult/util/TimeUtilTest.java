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
        TimeUtil.toOffsetDateTime("1966-04-23T13:14:15.789Z");
    }

    @Test
    public void shouldParse_YYYYMMDD_HHMMSSsss01() {
        TimeUtil.toOffsetDateTime("1966-04-23T13:14:15.789+01");
    }

    @Test
    public void shouldParse_YYYYMMDD_HHMMSSsss() {
        TimeUtil.toOffsetDateTime("1966-04-23 13:14:15.789");
    }

    @Test
    public void shouldParse_YYYYMMDD_HHMMSS() {
        TimeUtil.toOffsetDateTime("1966-04-23 13:14:15");
    }

    @Test
    public void shouldParse_YYYYMMDD_HHMM() {
        TimeUtil.toOffsetDateTime("1966-04-23 13:14");
    }

    @Test
    public void shouldParse_YYYYMMDD_HHMMSS_Slash() {
        TimeUtil.toOffsetDateTime("1966/04/23 13:14:15");
    }

    @Test
    public void shouldParse_YYYYMMDD_HHMM_Slash() {
        TimeUtil.toOffsetDateTime("1966/04/23 13:14");
    }

    @Test
    public void shouldParse_DDMMYYYY_HHMMSSsss() {
        TimeUtil.toOffsetDateTime("23.04.1966 13:14:15.789");
    }

    @Test
    public void shouldParse_DDMMYYYY_HHMMSS() {
        TimeUtil.toOffsetDateTime("23.04.1966 13:14:15");
    }

    @Test
    public void shouldParse_DDMMYYYY_HHMM() {
        TimeUtil.toOffsetDateTime("23.04.1966 13:14");
    }

    @Test
    public void shouldParse_DDMMYY_HHMMSSsss() {
        TimeUtil.toOffsetDateTime("23.04.66 13:14:15.789");
    }

    @Test
    public void shouldParse_DDMMYY_HHMMSS() {
        TimeUtil.toOffsetDateTime("23.04.66 13:14:15");
    }

    @Test
    public void shouldParse_DDMMYY_HHMM() {
        TimeUtil.toOffsetDateTime("23.04.66 13:14");
    }
}
