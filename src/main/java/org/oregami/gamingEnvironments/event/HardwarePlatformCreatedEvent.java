package org.oregami.gamingEnvironments.event;

/**
 * Created by sebastian on 24.02.17.
 */
public class HardwarePlatformCreatedEvent {

    private final String newId;

    String workingTitle;

    public HardwarePlatformCreatedEvent(String newId, String workingTitle) {
        this.newId = newId;
        this.workingTitle = workingTitle;
    }

    public String getNewId() {
        return newId;
    }

    public String getWorkingTitle() {
        return workingTitle;
    }
}
