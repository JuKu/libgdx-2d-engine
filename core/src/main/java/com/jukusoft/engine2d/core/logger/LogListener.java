package com.jukusoft.engine2d.core.logger;

@FunctionalInterface
public interface LogListener {

    /**
     * log message
     *
     * @param str log message string
     */
    public void log(String str);

}
