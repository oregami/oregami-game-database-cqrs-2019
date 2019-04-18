package org.oregami.references.command;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class AddEventIdCommand {

    @TargetAggregateIdentifier
    private final String referenceId;

    private String eventId;

    public AddEventIdCommand(String referenceId, String eventId) {
        this.referenceId = referenceId;
        this.eventId = eventId;
    }

}
