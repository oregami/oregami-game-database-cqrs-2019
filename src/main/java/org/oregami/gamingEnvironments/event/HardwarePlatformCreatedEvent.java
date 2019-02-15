package org.oregami.gamingEnvironments.event;

import org.oregami.gamingEnvironments.model.types.HardwarePlatformType;

/**
 * Created by sebastian on 24.02.17.
 */
public class HardwarePlatformCreatedEvent {

    private final String newId;

    private String workingTitle;

    private final HardwarePlatformType hardwarePlatformType;

    public HardwarePlatformCreatedEvent(String newId, String workingTitle, HardwarePlatformType hardwarePlatformType) {
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
