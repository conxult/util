/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.conxult.workflow;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 *
 * @author joergh
 */
@Getter @Setter @Accessors(chain = true)
public class UserSignupResponse
    extends FailureMapResponse {

    UUID userId;
    UUID dependentId;
    
}
