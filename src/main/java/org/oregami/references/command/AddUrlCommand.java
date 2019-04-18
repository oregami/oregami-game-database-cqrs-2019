package org.oregami.references.command;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
//@CommandValidator(GamingEnvironmentValidator.class)
public class AddUrlCommand {

    @TargetAggregateIdentifier
    private final String referenceId;

    private String url;

    public AddUrlCommand(String referenceId, String url) {
        this.referenceId = referenceId;
        this.url = url;
    }

}
