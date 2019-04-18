package org.oregami.gamingEnvironments.command;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class AddHardwarePlatformToGamingEnvironmentCommand {

    @TargetAggregateIdentifier
    private final String gamingEnvironmentId;

    private String hardwarePlatformId;

    public AddHardwarePlatformToGamingEnvironmentCommand(String gamingEnvironmentId, String hardwarePlatformId) {
        this.gamingEnvironmentId = gamingEnvironmentId;
        this.hardwarePlatformId = hardwarePlatformId;
    }

    public String getHardwarePlatformId() {
        return hardwarePlatformId;
    }

    public String getGamingEnvironmentId() {
        return gamingEnvironmentId;
    }
}
