package org.oregami.gamingEnvironments.command;

/**
 * Created by sebastian on 24.02.17.
 */
public class CreateGamingEnvironmentCommand {

    private final String newId;

    String workingTitle;

    public CreateGamingEnvironmentCommand(String newId, String workingTitle) {
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
