package org.oregami.gamingEnvironments.command;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.oregami.common.CommandValidator;
import org.oregami.gamingEnvironments.model.GamingEnvironmentValidator;
import org.oregami.gamingEnvironments.model.types.Region;

@Data
@CommandValidator(GamingEnvironmentValidator.class)
public class AddRegionToHardwarePlatformCommand {

    @TargetAggregateIdentifier
    private final String hardwarePlatformId;

    private Region region;

    public AddRegionToHardwarePlatformCommand(String hardwarePlatformId, Region region) {
        this.hardwarePlatformId = hardwarePlatformId;
        this.region = region;
    }

    public String getHardwarePlatformId() {
        return hardwarePlatformId;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
}
