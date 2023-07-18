/*
 * Copyright by https://conxult.de
 */
package de.conxult.log;

import java.util.logging.Level;

/**
 *
 * @author joerg
 */
public enum LogLevel {
    TRACE(Level.FINER),
    DEBUG(Level.FINE),
    CONFIG(Level.CONFIG),
    INFO (Level.INFO),
    WARN (Level.WARNING),
    ERROR(Level.SEVERE);
    
    Level level;
    
    LogLevel(Level level) {
        this.level = level;
    }

    public Level getLevel() {
        return level;
    }
    
}
