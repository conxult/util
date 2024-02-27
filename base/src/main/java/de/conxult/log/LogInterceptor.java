package de.conxult.log;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import org.eclipse.microprofile.config.inject.ConfigProperty;


/**
 * Interceptor for class and method Level. Prints a LogMessage when the method is entered and exited with execution time.
 */
@Interceptor
@Priority(0)
@Log.Methods
public class LogInterceptor {

    final static int MAX_PARAMETER_LENGTH =  200;
    final static int MAX_RESULT_LENGTH    = 1000;

    @ConfigProperty(name = "application.id", defaultValue = "cx")
    String applicationId;

    @Inject
    @Log.Prefix("")
    Log logger;

    @AroundInvoke
    public Object intercept(InvocationContext ctx) throws Exception {
        Object result;
        long    startNanos    = System.nanoTime();
        String  methodName    = getMethodName(ctx);
        String  orgThreadName = setThreadName(ctx);
        logMethodEntry(methodName, ctx, LogLevel.INFO);
        try {
            result = ctx.proceed();
            logMethodSuccess(startNanos, methodName, result, LogLevel.INFO);
            return result;
        } catch (Exception ex) {
            logMethodException(startNanos, methodName, ex, ctx);
            throw ex;
        } finally {
            setThreadName(orgThreadName);
        }
    }

    void logMethodEntry(String methodName, InvocationContext ctx, LogLevel level) {
        logger.log(level, "{0}({1}) enter", methodName, getParametersAsString(ctx.getParameters()));
    }

    void logMethodSuccess(long startNanos, String methodName, Object result, LogLevel level) {
        logger.info("{0}(...) leave {1}", methodName, getTookMessage(startNanos));
    }

    void logMethodException(long startNanos, String methodName, Exception exception, InvocationContext ctx) {
        logger.warn("{0}(...) failed {1} with {2}", exception,
          methodName,
          getTookMessage(startNanos),
          (exception == null) ? "null" : getWarnMessage(exception)
        );
    }

    String getTookMessage(long startNanos) {
      return String.format("took %d ms", (System.nanoTime() - startNanos) / 1000_0000);
    }

    String getWarnMessage(Exception exception) {
        return exception.getClass().getSimpleName() + ": " + exception.getMessage();
    }

    String getParametersAsString(Object[] parameters) {
        if (parameters == null || parameters.length == 0) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        String delimiter = "";
        for (int i = 0; (i < parameters.length && result.length() < MAX_PARAMETER_LENGTH); i++) {
            Object parameter = parameters[i];
            result
                .append(delimiter)
                .append("[").append(i).append("] ")
                .append(parameter == null ? "null" : parameter.toString());
            delimiter = ", ";
        }
        if (result.length() <= MAX_PARAMETER_LENGTH) {
            return result.toString();
        }
        return result.substring(0, MAX_PARAMETER_LENGTH) + "\u2026";
    }

    /**
     * use this calling info if it's not a remote Call
     *
     * @param ctx
     * @return the String [SimpleClassname.methodName]
     */
    public static String getMethodName(InvocationContext ctx) {
        String methodName = "unknown";
        if (ctx.getMethod() != null) {
            methodName = ctx.getMethod().getName();
            if (ctx.getMethod().getDeclaringClass() != null) {
                methodName = ctx.getMethod().getDeclaringClass().getSimpleName() + "::" + methodName;
            }
        }
        return methodName;
    }

    String setThreadName(InvocationContext ctx) {
      String newThreadName = "~" + ctx.getMethod().getDeclaringClass().getSimpleName() + "@" + System.nanoTime();
      return setThreadName(newThreadName);
    }

    String setThreadName(String threadName) {
        String orgThreadName = Thread.currentThread().getName();
        if (threadName != null) {
            Thread.currentThread().setName(threadName);
        }
        return orgThreadName;
    }
}
