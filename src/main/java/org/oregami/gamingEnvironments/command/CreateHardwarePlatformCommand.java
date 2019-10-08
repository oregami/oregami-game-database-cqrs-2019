package org.oregami.gamingEnvironments.command;

import org.oregami.common.CommandValidator;
import org.oregami.gamingEnvironments.model.GamingEnvironmentValidator;
import org.oregami.gamingEnvironments.model.types.HardwarePlatformType;

/**
 * Created by sebastian on 24.02.17.
 */
@CommandValidator(GamingEnvironmentValidator.class)
public class CreateHardwarePlatformCommand {

    private final String newId;

    private String workingTitle;

    private HardwarePlatformType hardwarePlatformType;

    public CreateHardwarePlatformCommand(String newId, String workingTitle, HardwarePlatformType hardwarePlatformType) {
        this.newId = newId;
        this.workingTitle = workingTitle;
        this.hardwarePlatformType = hardwarePlatformType;
    }

    public String getNewId() {
        return newId;
    }

    public String getWorkingTitle() {
        return workingTitle;
    }

    public HardwarePlatformType getHardwarePlatformType() {
        return hardwarePlatformType;
    }
}
