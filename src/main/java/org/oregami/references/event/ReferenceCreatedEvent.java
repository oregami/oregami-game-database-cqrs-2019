package org.oregami.references.event;

import org.oregami.references.model.types.ReferenceType;

import java.util.Set;

public class ReferenceCreatedEvent {

    private final String newId;

    ReferenceType referenceType;

    private final Set<String> eventIdSet;

    private final String url;

    private final String description;

    public ReferenceCreatedEvent(String newId, ReferenceType referenceType, Set<String> eventIdSet, String url, String description) {
        this.newId = newId;
        this.referenceType = referenceType;
        this.eventIdSet = eventIdSet;
        this.url = url;
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

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }
}
