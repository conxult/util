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
 * @param <T> mapped value
 */
@FunctionalInterface
public interface RailwayMapper<V,T> {
    
    public T map(V from) throws Exception;
    
}
