package org.oregami.gamingEnvironments.application;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.oregami.gamingEnvironments.command.CreateHardwarePlatformCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * Created by sebastian on 14.02.17.
 */
@Service
public class HardwarePlatformApplicationService {

    private CommandGateway commandGateway;

    @Autowired
    public HardwarePlatformApplicationService(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    public CompletableFuture<Object> createNewHardwarePlatform(String newId, String workingTitle) {
        return commandGateway.send(new CreateHardwarePlatformCommand(newId, workingTitle));
    }


}
