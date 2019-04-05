package org.oregami.references.event;

import org.oregami.references.model.types.ReferenceType;

public class ReferenceCreatedEvent {

    private final String newId;

    ReferenceType referenceType;

    public ReferenceCreatedEvent(String newId, ReferenceType referenceType) {
        this.newId = newId;
        this.referenceType = referenceType;
    }

    public String getNewId() {
        return newId;
    }

    public ReferenceType getReferenceType() {
        return referenceType;
    }
}
