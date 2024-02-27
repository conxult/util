/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.conxult.workflow;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

/**
 *
 * @author joergh
 * 
 * @param <This>
 */
public class FailureMapResponse<This extends FailureMapResponse> {
    
    Map<String, String> failures = new HashMap<>();

    public Map<String, String> getFailures() {
        return failures;
    }
   
    public This addFailure(String name, String reason) {
        failures.put(name, reason);
        return (This)this;
    }
    
}
