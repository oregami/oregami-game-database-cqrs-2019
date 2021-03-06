package org.oregami.gamingEnvironments.application;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.oregami.gamingEnvironments.command.AddHardwareModelToHardwarePlatformCommand;
import org.oregami.gamingEnvironments.command.AddRegionToHardwarePlatformCommand;
import org.oregami.gamingEnvironments.command.CreateHardwarePlatformCommand;
import org.oregami.gamingEnvironments.model.types.HardwarePlatformType;
import org.oregami.gamingEnvironments.model.types.Region;
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

    public CompletableFuture<Object> createNewHardwarePlatform(String newId, String workingTitle, HardwarePlatformType hardwarePlatformType) {
        return commandGateway.send(new CreateHardwarePlatformCommand(newId, workingTitle, hardwarePlatformType));
    }

    public CompletableFuture<Object> addHardwareModelToHardwarePlatform(String hardwarePlatformId, String hardwareModelId) {
        return commandGateway.send(new AddHardwareModelToHardwarePlatformCommand(hardwarePlatformId, hardwareModelId));
    }

    public CompletableFuture<Object> addRegionToHardwarePlatform(String hardwarePlatformId, Region region) {
        return commandGateway.send(new AddRegionToHardwarePlatformCommand(hardwarePlatformId, region));
    }

}
