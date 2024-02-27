/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.conxult.workflow;

/**
 *
 * @author joergh
 *
 * @param <V> value
 */
public class Failure<V>
    extends Railway<V> {

    boolean notified = false;

    Failure(V value, Exception failure) {
        super(value, failure);
    }

    @Override
    public <T> Railway<T> onSuccess(RailwayMapper<V,T> map) {
        return (Railway<T>) this;
    }

    @Override
    public Railway<V> onFailure(RailwayNotifier<V> onFailure) {
        if (!notified) {
            onFailure.failure(failure);
            notified = true;
        }
        return this;
    }

    @Override
    public Railway<V> verify(RailwayVerifier<V> checker, RailwayMapper<V, Exception> exceptionProducer) {
        return this;
    }

}
