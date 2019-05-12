package org.oregami.gamingEnvironments.event;

/**
 * Created by sebastian on 24.02.17.
 */
public class HardwareModelAddedToHardwarePlatformEvent {

    private final String hardwarePlatformId;
    private String hardwareModelId;

    public HardwareModelAddedToHardwarePlatformEvent(String hardwarePlatformId, String hardwareModelId) {
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
