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
 *
 */
public class Success<V>
    extends Railway<V> {

    Success(V value) {
        super(value, null);
    }

    @Override
    public <T> Railway<T> onSuccess(RailwayMapper<V,T> map) {
        try {
            return new Success(map.map(value));
        } catch (Exception failure) {
            return new Failure(value, failure);
        }
    }

    @Override
    public Railway<V> onFailure(RailwayNotifier<V> onFailure) {
        return this;
    }

    @Override
    public Railway<V> verify(RailwayVerifier<V> checker, RailwayMapper<V, Exception> exceptionSupplier) {
        if (checker.verify(value)) {
            return this;
        }
        try {
            return (exceptionSupplier == null) ? new Failure(value, null) : new Failure(value, exceptionSupplier.map(value));
        } catch (Exception mapException) {
            return new Failure(value, mapException);
        }
    }



}
