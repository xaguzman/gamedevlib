package com.xaguzman.artemiscommons.systems;

import com.artemis.BaseSystem;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.xaguzman.artemiscommons.events.Event;
import com.xaguzman.artemiscommons.events.EventListener;

/**
 * System which allows listeners to respond to messages sent by other systems.
 * Events are sent on the next execution of the {@link #processSystem()} method after the {@link #broadCast(Event)}
 * method was called (could be on next world's processing cycle).
 */
public class EventsSystem extends BaseSystem {

    private Array<Event> queuedEvents;
    private IntMap<Array<EventListener>> listeners;

    public EventsSystem(){
        queuedEvents = new Array<Event>();
        listeners = new IntMap<Array<EventListener>>();
    }

    public void broadCast(Event event){
        queuedEvents.add(event);
    }

    public void addListener(int eventType, EventListener listener) {
        Array<EventListener> listeners = this.listeners.get(eventType);
        if (listeners == null){
            listeners = new Array<EventListener>();
            this.listeners.put(eventType, listeners);
        }
        listeners.add(listener);
    }

    @Override
    protected void processSystem() {
        for(Event event : queuedEvents){
            if (!listeners.containsKey(event.type))
                continue;

            Array<EventListener> eListeners = listeners.get(event.type);
            if (eListeners != null){
                for(EventListener listener : eListeners)
                    listener.handle(event);
            }
        }

        queuedEvents.clear();
    }

    @Override
    protected boolean checkProcessing() {
        return queuedEvents.size > 0;
    }
}
