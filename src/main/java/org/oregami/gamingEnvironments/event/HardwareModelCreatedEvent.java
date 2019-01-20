package org.oregami.gamingEnvironments.event;

/**
 * Created by sebastian on 24.02.17.
 */
public class HardwareModelCreatedEvent {

    private final String newId;

    String workingTitle;

    public HardwareModelCreatedEvent(String newId, String workingTitle) {
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
