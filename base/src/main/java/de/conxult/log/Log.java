package de.conxult.log;

import jakarta.interceptor.InterceptorBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.logging.Logger;

/**
 *
 * @author joerg
 */
public class Log {

  Logger logger;

  String prefix = "";

  static Map<String, Logger> instances = new HashMap<>();

  public Log(Logger logger) {
    this.logger = logger;
  }

  public static Log instance(Class<?> type) {
    return instance(type.getSimpleName());
  }

  public static Log instance(String name) {
    return instance(name, LogLevel.INFO);
  }

  public static Log instance(String name, LogLevel defaultLevel) {
    synchronized (instances) {
      Logger logger = instances.computeIfAbsent(name, (n) -> Logger.getLogger(name));
      logger.setLevel(findLogLevel(name, defaultLevel).getLevel());
      return new Log(logger);
    }
  }

  public Log setPrefix(String prefix) {
    this.prefix = (prefix.isEmpty()) ? prefix : prefix + ":";
    return this;
  }

  public Log config(String message, Object... parameters) {
    return log(LogLevel.CONFIG, message, parameters);
  }

  public Log config(Throwable throwable, String message, Object... parameters) {
    return log(LogLevel.CONFIG, throwable, message, parameters);
  }

  public Log trace(String message, Object... parameters) {
    return log(LogLevel.TRACE, message, parameters);
  }

  public Log trace(Throwable throwable, String message, Object... parameters) {
    return log(LogLevel.TRACE, throwable, message, parameters);
  }

  public Log debug(String message, Object... parameters) {
    return log(LogLevel.DEBUG, message, parameters);
  }

  public Log debug(Throwable throwable, String message, Object... parameters) {
    return log(LogLevel.DEBUG, throwable, message, parameters);
  }

  public Log info(String message, Object... parameters) {
    return log(LogLevel.INFO, message, parameters);
  }

  public Log info(Throwable throwable, String message, Object... parameters) {
    return log(LogLevel.INFO, throwable, message, parameters);
  }

  public Log warn(String message, Object... parameters) {
    return log(LogLevel.WARN, message, parameters);
  }

  public Log warn(Throwable throwable, String message, Object... parameters) {
    return log(LogLevel.WARN, throwable, message, parameters);
  }

  public Log error(String message, Object... parameters) {
    return log(LogLevel.ERROR, message, parameters);
  }

  public Log error(Throwable throwable, String message, Object... parameters) {
    return log(LogLevel.ERROR, throwable, message, parameters);
  }

  public Log log(LogLevel level, String message, Object... parameters) {
    if (logger.isLoggable(level.getLevel())) {
      logger.log(level.getLevel(), getMessage(prefix + message, parameters));
    }
    return this;
  }

  public Log log(LogLevel level, Supplier<String> messageSupplier) {
    if (logger.isLoggable(level.getLevel())) {
      logger.log(level.getLevel(), prefix + messageSupplier.get());
    }
    return this;
  }

  public Log log(LogLevel level, Throwable throwable, String message, Object... parameters) {
    if (logger.isLoggable(level.getLevel())) {
      logger.log(level.getLevel(), throwable, () -> getMessage(prefix + message, parameters));
    }
    return this;
  }

  String getMessage(String message, Object... parameters) {
    if (parameters == null || parameters.length == 0) {
        return message;
    } else if (message.contains("{0}")) {
        return MessageFormat.format(message, parameters);
    } else {
        return message.formatted(parameters);
    }
  }

  static LogLevel findLogLevel(String name, LogLevel defaultLogLevel) {
    String level = System.getProperty("cx.log."+name+".level");
    if  (level == null) {
      level = System.getProperty("cx.log.level");
    }
    return (level == null) ? defaultLogLevel : LogLevel.valueOf(level);
  }

  static {
    System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT.%1$tL] [%4$-7s] %5$s %n");
  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.FIELD})
  public @interface Prefix {
    String value();
  }

  @Inherited
  @InterceptorBinding
  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.TYPE, ElementType.METHOD})
  public @interface Methods {
  }

}
