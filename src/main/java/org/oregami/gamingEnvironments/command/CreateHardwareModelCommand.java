package org.oregami.gamingEnvironments.command;

/**
 * Created by sebastian on 24.02.17.
 */
public class CreateHardwareModelCommand {

    private final String newId;

    String workingTitle;

    public CreateHardwareModelCommand(String newId, String workingTitle) {
        this.newId = newId;
        this.workingTitle = workingTitle;
    }

    public String getId() {
        return newId;
    }

    public String getWorkingTitle() {
        return workingTitle;
    }
}
