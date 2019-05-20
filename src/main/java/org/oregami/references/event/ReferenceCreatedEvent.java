package org.oregami.references.event;

import org.oregami.references.model.types.ReferenceType;

import java.util.Set;

public class ReferenceCreatedEvent {

    private final String newId;

    ReferenceType referenceType;

    private final Set<String> eventIdSet;

    private final String description;

    public ReferenceCreatedEvent(String newId, ReferenceType referenceType, Set<String> eventIdSet, String description) {
        this.newId = newId;
        this.referenceType = referenceType;
        this.eventIdSet = eventIdSet;
        this.description = description;
    }

    public String getNewId() {
        return newId;
    }

    public ReferenceType getReferenceType() {
        return referenceType;
    }

    public Set<String> getEventIdSet() {
        return eventIdSet;
    }

    public String getDescription() {
        return description;
    }
}
