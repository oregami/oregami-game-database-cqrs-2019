package org.oregami.gamingEnvironments.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class AddHardwareModelToHardwarePlatformCommand {

    @TargetAggregateIdentifier
    private final String hardwarePlatformId;

    private String hardwareModelId;

    public AddHardwareModelToHardwarePlatformCommand(String hardwarePlatformId, String hardwareModelId) {
        this.hardwarePlatformId = hardwarePlatformId;
        this.hardwareModelId = hardwareModelId;
    }

    public String getHardwarePlatformId() {
        return hardwarePlatformId;
    }

    public String getHardwareModelId() {
        return hardwareModelId;
    }
}
