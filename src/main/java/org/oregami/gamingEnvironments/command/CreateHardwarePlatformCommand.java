package org.oregami.gamingEnvironments.command;

/**
 * Created by sebastian on 24.02.17.
 */
public class CreateHardwarePlatformCommand {

    private final String newId;

    String workingTitle;

    public CreateHardwarePlatformCommand(String newId, String workingTitle) {
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
