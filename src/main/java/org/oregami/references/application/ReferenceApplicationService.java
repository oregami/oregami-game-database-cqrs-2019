package org.oregami.references.application;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.oregami.references.command.AddDescriptionCommand;
import org.oregami.references.command.AddEventIdCommand;
import org.oregami.references.command.AddUrlCommand;
import org.oregami.references.command.CreateReferenceCommand;
import org.oregami.references.model.types.ReferenceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
public class ReferenceApplicationService {

    private CommandGateway commandGateway;

    @Autowired
    public ReferenceApplicationService(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    public CompletableFuture<Object> createNewReference(String newId, ReferenceType referenceType, Set<String> eventIdSet, String url, String description) {
        return commandGateway.send(new CreateReferenceCommand(newId, referenceType, eventIdSet, url, description));
    }


    public CompletableFuture<Object> addUrl(String referenceId, String url) {
        return commandGateway.send(new AddUrlCommand(referenceId, url));
    }

    public CompletableFuture<Object> addDescription(String referenceId, String description) {
        return commandGateway.send(new AddDescriptionCommand(referenceId, description));
    }

    public CompletableFuture<Object> addEventId(String referenceId, String eventId) {
        return commandGateway.send(new AddEventIdCommand(referenceId, eventId));
    }


}
