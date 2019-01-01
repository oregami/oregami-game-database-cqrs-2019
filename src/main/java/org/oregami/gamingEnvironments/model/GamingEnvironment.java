package org.oregami.gamingEnvironments.model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.oregami.gamingEnvironments.command.AddHardwarePlatformToGamingEnvironmentCommand;
import org.oregami.gamingEnvironments.command.AddYearOfFirstReleaseCommand;
import org.oregami.gamingEnvironments.command.ChangeYearOfFirstReleaseCommand;
import org.oregami.gamingEnvironments.command.CreateGamingEnvironmentCommand;
import org.oregami.gamingEnvironments.event.GamingEnvironmentCreatedEvent;
import org.oregami.gamingEnvironments.event.HardwarePlatformAddedToGamingEnvironmentEvent;
import org.oregami.gamingEnvironments.event.YearOfFirstReleaseAddedEvent;
import org.oregami.gamingEnvironments.event.YearOfFirstReleaseChangedEvent;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sebastian on 03.11.16.
 */
@NoArgsConstructor
@Aggregate
@EqualsAndHashCode
public class GamingEnvironment {


    @AggregateIdentifier
    private String id;

    String workingTitle;

    Year yearOfFirstRelease;

    List<String> hardwarePlatformIdList = new ArrayList<>();


    @CommandHandler
    public GamingEnvironment(CreateGamingEnvironmentCommand command) {
        AggregateLifecycle.apply(new GamingEnvironmentCreatedEvent(command.getId(), command.getWorkingTitle()));
    }

    @EventSourcingHandler
    public void in(GamingEnvironmentCreatedEvent event) {
        this.id = event.getNewId();
    }


    @CommandHandler
    public void in(AddYearOfFirstReleaseCommand command) {
        AggregateLifecycle.apply(new YearOfFirstReleaseAddedEvent(command.getGamingEnvironmentId(), command.getYearOfFirstRelease()));
    }

    @EventSourcingHandler
    public void in(YearOfFirstReleaseAddedEvent event) {
        this.yearOfFirstRelease = event.getYearOfFirstRelease();
    }

    @CommandHandler
    public void in(ChangeYearOfFirstReleaseCommand command) {
        AggregateLifecycle.apply(new YearOfFirstReleaseChangedEvent(command.getId(), command.getYearOfFirstRelease()));
    }


    @EventSourcingHandler
    public void in(YearOfFirstReleaseChangedEvent event) {
        this.yearOfFirstRelease = event.getYearOfFirstRelease();
    }

    @CommandHandler
    public void in(AddHardwarePlatformToGamingEnvironmentCommand command) {
        AggregateLifecycle.apply(new HardwarePlatformAddedToGamingEnvironmentEvent(command.getGamingEnvironmentId(), command.getHardwarePlatformId()));
    }

    @EventSourcingHandler
    public void in(HardwarePlatformAddedToGamingEnvironmentEvent event) {
        this.hardwarePlatformIdList.add(event.getHardwarePlatformId());
    }

}
