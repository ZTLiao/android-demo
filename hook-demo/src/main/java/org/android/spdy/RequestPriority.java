package org.android.spdy;

public enum RequestPriority {

    HIGHEST(1),
    HIGH(2),
    MEDIUM(3),
    LOW(4),
    LOWEST(5),
    IDLE(6),
    DEFAULT_PRIORITY(3),
    
    ;

    RequestPriority(int priority) {
        this.priority = priority;
    }

    private int priority;

    public int getPriorityInt(){
        return this.priority;
    }
    
}
