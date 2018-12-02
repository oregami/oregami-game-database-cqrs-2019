package org.oregami.gamingEnvironments.application;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.oregami.gamingEnvironments.command.AddYearOfFirstReleaseCommand;
import org.oregami.gamingEnvironments.command.CreateGamingEnvironmentCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.concurrent.CompletableFuture;

/**
 * Created by sebastian on 14.02.17.
 */
@Service
public class GamingEnvironmentApplicationService {

    private CommandGateway commandGateway;

    @Autowired
    public GamingEnvironmentApplicationService(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    public CompletableFuture<Object> createNewGamingEnvironment(String newId, String workingTitle) {
        return commandGateway.send(new CreateGamingEnvironmentCommand(newId, workingTitle));
    }


    public CompletableFuture<Object> addYearOfFirstRelease(String gamingEnvironmentId, Year yearOfFirstRelease) {
        return commandGateway.send(new AddYearOfFirstReleaseCommand(gamingEnvironmentId, yearOfFirstRelease));
    }

}
