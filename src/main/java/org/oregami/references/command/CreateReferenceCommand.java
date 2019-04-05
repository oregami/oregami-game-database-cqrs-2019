package org.oregami.references.command;

import org.oregami.references.model.types.ReferenceType;

//@CommandValidator(GamingEnvironmentValidator.class)
public class CreateReferenceCommand {

    private final String newId;

    private final ReferenceType referenceType;


    public CreateReferenceCommand(String newId, ReferenceType referenceType) {
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
