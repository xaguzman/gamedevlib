package com.xguzm.artemiscommons.events;

public class Event {
    /** The event type. User-defined */
    public int type;
    public int[] sourceEntities;

    /** Some predefined recommendation for the event types */
    public static final class Types{
        public static final int COLLISION_STARTED = 1;
        public static final int COLLISION_ENDED = 2;
        public static final int INTERACTION_STARTED = 3;
        public static final int INTERACTION_COMPLETED = 4;
        public static final int INTERACTION_CANCELLED = 5;
    }
}
