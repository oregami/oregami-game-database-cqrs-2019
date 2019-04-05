package org.oregami.references.command;

import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Data
//@CommandValidator(GamingEnvironmentValidator.class)
public class AddDescriptionCommand {

    @TargetAggregateIdentifier
    private final String referenceId;

    private String description;

    public AddDescriptionCommand(String referenceId, String description) {
        this.referenceId = referenceId;
        this.description = description;
    }

}
