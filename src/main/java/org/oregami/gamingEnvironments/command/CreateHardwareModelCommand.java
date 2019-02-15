package org.oregami.gamingEnvironments.command;

import org.oregami.common.CommandValidator;
import org.oregami.gamingEnvironments.model.GamingEnvironmentValidator;
import org.oregami.gamingEnvironments.model.types.HardwareModelType;

/**
 * Created by sebastian on 24.02.17.
 */
@CommandValidator(GamingEnvironmentValidator.class)
public class CreateHardwareModelCommand {

    private final String newId;

    private String workingTitle;

    private HardwareModelType hardwareModelType;

    public CreateHardwareModelCommand(String newId, String workingTitle, HardwareModelType hardwareModelType) {
        this.newId = newId;
        this.workingTitle = workingTitle;
        this.hardwareModelType = hardwareModelType;
    }

    public String getId() {
        return newId;
    }

    public String getWorkingTitle() {
        return workingTitle;
    }

    public HardwareModelType getHardwareModelType() {
        return hardwareModelType;
    }
}
