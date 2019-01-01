package org.oregami.gamingEnvironments.event;

import java.time.Year;

/**
 * Created by sebastian on 24.02.17.
 */
public class HardwarePlatformAddedToGamingEnvironmentEvent {

    private final String gamingEnvironmentId;
    private String hardwarePlatformId;

    public HardwarePlatformAddedToGamingEnvironmentEvent(String gamingEnvironmentId, String hardwarePlatformId) {
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
