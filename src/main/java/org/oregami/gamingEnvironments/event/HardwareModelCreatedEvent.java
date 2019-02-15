package org.oregami.gamingEnvironments.event;

import org.oregami.gamingEnvironments.model.types.HardwareModelType;

/**
 * Created by sebastian on 24.02.17.
 */
public class HardwareModelCreatedEvent {

    private final String newId;
    private String workingTitle;
    private final HardwareModelType hardwareModelType;

    public HardwareModelCreatedEvent(String newId, String workingTitle, HardwareModelType hardwareModelType) {
        this.newId = newId;
        this.workingTitle = workingTitle;
        this.hardwareModelType = hardwareModelType;
    }

    public String getNewId() {
        return newId;
    }

    public String getWorkingTitle() {
        return workingTitle;
    }

    public HardwareModelType getHardwareModelType() {
        return hardwareModelType;
    }
}
