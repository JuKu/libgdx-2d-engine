package com.jukusoft.engine2d.core.events;

@FunctionalInterface
public interface EventListener<T extends EventData> {

    /**
    * handle event
     *
     * @param eventData single event
    */
    public void handleEvent(T eventData);

}
