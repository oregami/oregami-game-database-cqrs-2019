package org.oregami.references.command;

import org.oregami.references.model.types.ReferenceType;

import java.util.Set;

//@CommandValidator(GamingEnvironmentValidator.class)
public class CreateReferenceCommand {

    private final String newId;

    private final ReferenceType referenceType;

    private final Set<String> eventIdSet;

    private final String url;

    private final String description;


    public CreateReferenceCommand(String newId, ReferenceType referenceType, Set<String> eventIdSet, String url, String description) {
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
