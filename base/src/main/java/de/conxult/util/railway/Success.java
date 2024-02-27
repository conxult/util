package de.conxult.util.railway;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Success<V, F> extends Result<V, F> {

    private final Optional<V> value;

    public Success(final V value) {
        this.value = Optional.ofNullable(value);
    }

    @Override
    public boolean isFailure() {
        return false;
    }

    @Override
    public V getValue() {
        if (value.isPresent()) {
            return value.get();
        }
        throw new EmptyResultHasNoValueException();
    }

    @Override
    public F getError() {
        throw new SuccessfulResultHasNoErrorException();
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder("Result (");
        result.append("Success");
        if (value.isPresent()) {
            result.append(" with value <");
            result.append(getValue());
            result.append('>');
        }
        result.append(')');
        return result.toString();
    }

    @Override
    public Result<?, F> combine(
      final Result<?, F> otherResult) {
        if (otherResult.isFailure()) {
            return otherResult;
        }
        return this;
    }

    @Override
    public <T> Result<T, F> onSuccess(
      final Supplier<Result<T, F>> function) {
        return function.get();
    }

    @Override
    public <T> Result<T, F> onSuccess(
      final Supplier<T> function, final Class<T> clazz) {
        return onSuccess(() -> new Success<>(function.get()));
    }

    @Override
    public Result<V, F> onSuccess(final Consumer<V> function) {
        function.accept(getValue());
        return this;
    }

    @Override
    public Result<V, F> ensure(
      final Predicate<V> predicate, final F error) {
        try {
            if (!predicate.test(getValue())) {
                return new Failure<>(error);
            }
        } catch (final EmptyResultHasNoValueException exception) {
            throw exception;
        } catch (final Exception exception) {
            return new Failure<>(error);
        }
        return this;
    }

    @Override
    public <T> Result<T, F> flatMap(final Function<V, Result<T, F>> function) {
        return function.apply(getValue());
    }

    @Override
    public <T> Result<T, F> map(final Function<V, T> function) {
        return flatMap(function.andThen(value -> new Success<T, F>(value)));
    }

    @Override
    public <T> Result<T, F> ifValueIsPresent(
      final Class<T> innerValue, final F error) {
        if (!(getValue() instanceof Optional)) {
            return new Failure<>(error);
        }
        @SuppressWarnings("unchecked")
        final Optional<T> optional = (Optional<T>) getValue();
        if (!optional.isPresent()) {
            return new Failure<>(error);
        }
        return new Success<>(optional.get());
    }

    @Override
    public Result<V, F> onFailure(final Runnable function) {
        return this;
    }

    @Override
    public Result<V, F> onFailure(final Consumer<F> function) {
        return this;
    }

    @Override
    public Result<V, F> onFailure(
      final Predicate<F> predicate, final Consumer<F> function) {
        return this;
    }
}
