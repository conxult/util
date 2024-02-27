/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.conxult.workflow;

/**
 *
 * @author joerg
 *
 * @param <V> value
 *
 */
abstract public class Railway<V> {

    V         value;
    Exception failure;

    abstract public <T> Railway<T> onSuccess(RailwayMapper<V,T> map);
    abstract public Railway<V> onFailure(RailwayNotifier<V> notifier);

    public Railway<V> verify(RailwayVerifier<V> checker) {
        return verify(checker, null);
    }

    abstract public Railway<V> verify(RailwayVerifier<V> checker, RailwayMapper<V, Exception> exceptionProducer);

    public V getValue() {
        return value;
    }

    public Exception getFailure() {
        return failure;
    }

    public boolean isFailure() {
        return this instanceof Failure;
    };

    public boolean isSuccess() {
        return this instanceof Success;
    }

    public static <V> Railway<V> of(V value) {
        return new Success(value);
    }

    Railway(V value, Exception failure) {
        this.value = value;
        this.failure = failure;
    }

}
