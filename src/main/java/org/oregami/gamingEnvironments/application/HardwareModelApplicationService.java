package org.oregami.gamingEnvironments.application;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.oregami.gamingEnvironments.command.CreateHardwareModelCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * Created by sebastian on 14.02.17.
 */
@Service
public class HardwareModelApplicationService {

    private CommandGateway commandGateway;

    @Autowired
    public HardwareModelApplicationService(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    public CompletableFuture<Object> createNewHardwareModel(String newId, String workingTitle) {
        return commandGateway.send(new CreateHardwareModelCommand(newId, workingTitle));
    }


}
