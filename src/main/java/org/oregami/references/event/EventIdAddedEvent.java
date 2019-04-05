package org.oregami.references.event;

public class EventIdAddedEvent {

    private final String id;
    private String eventId;

    public EventIdAddedEvent(String id, String eventId) {
        this.id = id;
        this.eventId = eventId;
    }

    public String getId() {
        return id;
    }

    public String getEventId() {
        return eventId;
    }
}
