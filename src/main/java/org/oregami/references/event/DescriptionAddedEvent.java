package org.oregami.references.event;

public class DescriptionAddedEvent {

    private final String id;
    private String description;

    public DescriptionAddedEvent(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
