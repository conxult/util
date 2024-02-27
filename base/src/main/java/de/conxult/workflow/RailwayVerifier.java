/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package de.conxult.workflow;

/**
 *
 * @author joergh
 * 
 * @param <V> value
 * 
 */
@FunctionalInterface
public interface RailwayVerifier<V> {
    
    public boolean verify(V value);
    
}
