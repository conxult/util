/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.conxult.log;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.Annotated;
import jakarta.enterprise.inject.spi.InjectionPoint;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author joergh
 */
@ApplicationScoped
public class LogFactory {

    @ConfigProperty(name = "application.id", defaultValue = "cx")
    String applicationId;

    @Produces
    Log createLogger(InjectionPoint ip) {
        // get the logger first
        Annotated annotated = ip.getAnnotated();
        Log logger = Log.instance(applicationId);

        // set the prefix
        Log.Prefix loggerPrefix = annotated.getAnnotation(Log.Prefix.class);
        if (loggerPrefix == null) {
            logger.setPrefix(ip.getMember().getDeclaringClass().getSimpleName() + "::");
        } else if (loggerPrefix.value().isEmpty()) {
            logger.setPrefix(loggerPrefix.value());
        } else {
            logger.setPrefix(loggerPrefix.value() + "::");
        }
        return logger;
    }

}
