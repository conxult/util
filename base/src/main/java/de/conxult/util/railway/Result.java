package de.conxult.util.railway;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Result of a computation or any other action. Can be successful and contain a value (V) or failed and contain an error
 * (F).
 *
 * @param <V> The type of the contained value.
 * @param <F> The type of the error object in case of a failure.
 */
public abstract class Result<V, F> {

    /**
     * Creates a new Result with the given error.
     *
     * @param error The error.
     * @return Failed Result.
     */
    public static <V, F> Result<V, F> withError(final F error) {
        assertParameterNotNull(error, "Error");
        return new Failure<>(error);
    }

    /**
     * Creates a new Result without a value.
     *
     * @return Successful Result.
     */
    public static <F> Result<Void, F> withoutValue() {
        return new Success<>(null);
    }

    /**
     * Creates a successful Result with the given value. The value may not be null.
     *
     * @param value The value.
     * @return Successful Result with the given value.
     * @throws IllegalArgumentException If value is null.
     */
    public static <V, F> Result<V, F> withValue(final V value) {
        assertParameterNotNull(value, "Value");
        return new Success<>(value);
    }

    /**
     * Creates a Result with an optional value. If the value is null, a failed Result will be created.
     *
     * @param value The value.
     * @param error The error to set, if value is null.
     * @return Successful Result with value or failed Result.
     */
    public static <V, F> Result<V, F> with(final V value, final F error) {
        if (value != null) {
            return withValue(value);
        }
        return withError(error);
    }

    /**
     * Creates a Result with an optional value. If the value is null or an empty Optional, a failed Result is created.
     *
     * @param valueOrNothing The optional value.
     * @param error The error to set, if value is null or an empty Optional.
     * @return Successful Result with value or failed Result.
     */
    public static <V, F> Result<V, F> with(
      final Optional<V> valueOrNothing, final F error) {
        if (valueOrNothing == null || !valueOrNothing.isPresent()) {
            return withError(error);
        }
        return withValue(valueOrNothing.get());
    }

    protected static void assertParameterNotNull(final Object parameter, final String name) {
        if (parameter == null) {
            throw new IllegalArgumentException(String.format("%s may not be null.", name));
        }
    }

    /**
     * Checks whether the Result is failed.
     *
     * @return Whether the Result is failed.
     */
    public abstract boolean isFailure();

    /**
     * Checks whether the Result is successful.
     *
     * @return Whether the Result is successful.
     */
    public final boolean isSuccess() {
        return !isFailure();
    }

    /**
     * Returns the Result's value.
     *
     * @return The value.
     * @throws FailedResultHasNoValueException If Result is failed.
     * @throws EmptyResultHasNoValueException If Result does not have a value.
     */
    public abstract V getValue();

    /**
     * Returns the Result's error.
     *
     * @return The error.
     * @throws SuccessfulResultHasNoErrorException If Result is successful.
     */
    public abstract F getError();

    /**
     * Returns the Result as a string.
     *
     * <pre>
     * Result (Success with value &lt;The value&gt;)
     * </pre>
     *
     * <pre>
     * Result (Error: The error)
     * </pre>
     *
     * @return The Result as a string.
     */
    @Override
    public abstract String toString();

    /**
     * Combines multiple Results. Returns the first failed Result or a successful Result without a value, if all Results
     * are successful.
     *
     * @param otherResult The Result to combine with the current one.
     * @return Result of the combination.
     */
    public abstract Result<?, F> combine(final Result<?, F> otherResult);

    /**
     * Runs the given function, if the Result is successful.
     *
     * @param function The function to run.
     * @return Result of the function.
     */
    public abstract <T> Result<T, F> onSuccess(
      final Supplier<Result<T, F>> function);

    /**
     * Runs the given function and wraps its return value in a Result, if the Result is successful.
     *
     * @param function The function to run.
     * @param clazz The return value of the function.
     * @return Return value of the function wrapped in a Result.
     */
    public abstract <T> Result<T, F> onSuccess(
      final Supplier<T> function, final Class<T> clazz);

    /**
     * Runs the given function, if the Result is successful.
     *
     * @param function The function to run.
     * @return The current Result.
     */
    public abstract Result<V, F> onSuccess(final Consumer<V> function);

    /**
     * Runs the given function, if the Result is failed.
     *
     * @param function The function to run.
     * @return The current Result.
     */
    public abstract Result<V, F> onFailure(final Runnable function);

    /**
     * Runs the given function, if the Result is failed.
     *
     * @param function The function to run.
     * @return The current Result.
     */
    public abstract Result<V, F> onFailure(final Consumer<F> function);

    /**
     * Runs the given function, if the Result is failed and the error matches the given predicate.
     *
     * @param predicate The predicate to check the error with.
     * @param function The function to run.
     * @return The current Result.
     */
    public abstract Result<V, F> onFailure(
      final Predicate<F> predicate, final Consumer<F> function);

    /**
     * Runs the given function, regardless of the Result's outcome.
     *
     * @param function The function to run.
     * @return The current Result.
     */
    public Result<V, F> onBoth(
      final Consumer<? super Result<V, F>> function) {
        function.accept(this);
        return this;
    }

    /**
     * Runs the given predicate, if the Result is successful.
     *
     * @param predicate The predicate to run.
     * @param error Error, if the predicate returns false.
     * @return Result with checked value or failed Result.
     * @throws EmptyResultHasNoValueException If the Result does not have a value.
     */
    public abstract Result<V, F> ensure(
      final Predicate<V> predicate, final F error);

    /**
     * Extracts a <code>Result&lt;T&gt;</code> from a <code>Result&lt;Result&lt;T&gt;&gt;</code>, if the Result is
     * successful.
     *
     * @param function A function that returns a <code>Result&lt;Result&lt;T&gt;&gt;</code>.
     * @return The extracted <code>Result&lt;T&gt;</code>.
     */
    public abstract <T> Result<T, F> flatMap(
      final Function<V, Result<T, F>> function);

    /**
     * Maps the Result to a Result with another value, if the Result is successful.
     *
     * @param function A function that returns the new value.
     * @return The Result of the function's value or a failed Result.
     */
    public abstract <T> Result<T, F> map(
      final Function<V, T> function);

    /**
     * Extracts the inner value from an Optional value of the Result.
     *
     * @param innerValue Type of the Optional's value.
     * @param error Error if inner value cannot be extracted.
     * @return Result of the inner value of the Optional or a failed Result.
     */
    public abstract <T> Result<T, F> ifValueIsPresent(
      final Class<T> innerValue, final F error);

    /**
     * This method takes some preconditions and test on null.
     *
     * @param results a list of results
     * @param <V> The type of the contained value.
     * @param <F> The type of the error object in case of a failure.
     * @return The last successful result or the first failure in chain.
     */
    public static <V, F> Result<V, F> combine(
      final Result<V, F>... results) {
        Result<V, F> lastResult = null;
        for (Result<V, F> result : results) {
            lastResult = result;
            if (result.isFailure()) {
                return result;
            }
        }
        return lastResult;
    }
}
