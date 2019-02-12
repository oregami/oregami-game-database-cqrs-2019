package org.oregami.gamingEnvironments.command;

import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.oregami.common.CommandValidator;
import org.oregami.gamingEnvironments.model.GamingEnvironmentValidator;

import java.time.Year;

@Data
@CommandValidator(GamingEnvironmentValidator.class)
public class AddYearOfFirstReleaseCommand {

    @TargetAggregateIdentifier
    private final String gamingEnvironmentId;

    private Year yearOfFirstRelease;

    public AddYearOfFirstReleaseCommand(String gamingEnvironmentId, Year yearOfFirstRelease) {
        this.gamingEnvironmentId = gamingEnvironmentId;
        this.yearOfFirstRelease = yearOfFirstRelease;
    }

    public Year getYearOfFirstRelease() {
        return yearOfFirstRelease;
    }

    public String getGamingEnvironmentId() {
        return gamingEnvironmentId;
    }
}
