package de.conxult.util;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class TimeUtil {
    static List<String> dateFormats = List.of("yyyy-MM-dd", "yyyy/MM/dd", "dd.MM.yyyy", "dd.MM.yy");
    static List<String> timeFormats = List.of("HH:mm:ss.SSS", "HH:mm:ss", "HH:mm");

    static List<DateTimeFormatter> formatters = new ArrayList<>();

    static {
        for (String dateFormat : dateFormats) {
            for (String timeFormat : timeFormats) {
                formatters.add(DateTimeFormatter.ofPattern(dateFormat + "'T'" + timeFormat + "x"));
            }
        }
    }

    public static String toString(OffsetDateTime t) {
      return formatters.get(0).format(t);
    }

    public static OffsetDateTime toOffsetDateTime(String text) {
        DateTimeParseException firstException = null;
        try {
            return OffsetDateTime.parse(text);
        } catch (DateTimeParseException dateTimeParseException) {
            firstException = dateTimeParseException;
        }

        text = text.replace(" ", "T");
        if (!text.contains("T")) {
            text += "T";
        }
        if (!text.contains(":")) {
            text += "00:00:00";
        }
        if (!text.contains("Z") && !text.contains("+")) {
            text += "+00";
        }

        for (DateTimeFormatter formatter : formatters) {
            try {
                return OffsetDateTime.parse(text, formatter);
            } catch (DateTimeParseException dateTimeParseException) {
            }
        }
        
        throw firstException;
    }

    public static OffsetDateTime toOffsetDateTime(Instant instant) {
        return ZonedDateTime.ofInstant(instant, ZoneId.systemDefault()).toOffsetDateTime();
    }

    /*
     static DateTimeFormatter formatter = new DateTimeFormatterBuilder()
        // date/time
        .appendPattern("yyyy-MM-dd'T'HH:mm:ss")
        .optionalStart()
        .appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true)
        .optionalEnd()
        .appendPattern("x")
        .toFormatter();

    public static OffsetDateTime toOffsetDateTime(String text) {
        text = text.replace(' ', 'T');
        if (!text.contains("T")) {
            text += "T";
        }
        if (!text.contains(":")) {
            text += "00:00:00";
        }
        if (!text.contains("Z") && !text.contains("+")) {
            text += "+00";
        }
        return OffsetDateTime.parse(text, formatter);
    }

    */
}
