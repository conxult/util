package de.conxult.util.railway;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Failure<V, F> extends Result<V, F> {

    private final F error;

    public Failure(final F error) {
        this.error = error;
    }

    @Override
    public boolean isFailure() {
        return true;
    }

    @Override
    public V getValue() {
        throw new FailedResultHasNoValueException(getError());
    }

    @Override
    public F getError() {
        return error;
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder("Result (");
        result.append("Error: ");
        result.append(getError());
        result.append(')');
        return result.toString();
    }

    @Override
    public Result<?, F> combine(
      final Result<?, F> otherResult) {
        return this;
    }

    @Override
    public <T> Result<T, F> onSuccess(
      final Supplier<Result<T, F>> function) {
        return new Failure<>(getError());
    }

    @Override
    public <T> Result<T, F> onSuccess(
      final Supplier<T> function, final Class<T> clazz) {
        return new Failure<>(getError());
    }

    @Override
    public Result<V, F> onSuccess(final Consumer<V> function) {
        return this;
    }

    @Override
    public Result<V, F> ensure(
      final Predicate<V> predicate, final F error) {
        return this;
    }

    @Override
    public <T> Result<T, F> flatMap(final Function<V, Result<T, F>> function) {
        return new Failure<>(getError());
    }

    @Override
    public <T> Result<T, F> map(final Function<V, T> function) {
        return new Failure<>(getError());
    }

    @Override
    public <T> Result<T, F> ifValueIsPresent(
      final Class<T> innerValue, final F error) {
        return new Failure<>(getError());
    }

    @Override
    public Result<V, F> onFailure(final Runnable function) {
        function.run();
        return this;
    }

    @Override
    public Result<V, F> onFailure(final Consumer<F> function) {
        function.accept(getError());
        return this;
    }

    @Override
    public Result<V, F> onFailure(
      final Predicate<F> predicate, final Consumer<F> function) {
        if (predicate.test(getError())) {
            function.accept(getError());
        }
        return this;
    }
}
