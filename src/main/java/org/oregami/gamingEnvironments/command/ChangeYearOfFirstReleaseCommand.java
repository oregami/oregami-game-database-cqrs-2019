package org.oregami.gamingEnvironments.command;

import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.time.Year;

@Data
public class ChangeYearOfFirstReleaseCommand {

    @TargetAggregateIdentifier
    private final String id;

    private Year yearOfFirstRelease;

    public ChangeYearOfFirstReleaseCommand(String id, Year yearOfFirstRelease) {
        this.id = id;
        this.yearOfFirstRelease = yearOfFirstRelease;
    }

    public Year getYearOfFirstRelease() {
        return yearOfFirstRelease;
    }

    public String getId() {
        return id;
    }
}
