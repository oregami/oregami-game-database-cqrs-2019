package org.oregami.gamingEnvironments.model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.commandhandling.model.AggregateRoot;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.oregami.gamingEnvironments.command.AddYearOfFirstReleaseCommand;
import org.oregami.gamingEnvironments.command.CreateGamingEnvironmentCommand;
import org.oregami.gamingEnvironments.event.YearOfFirstReleaseAddedEvent;
import org.oregami.gamingEnvironments.event.GamingEnvironmentCreatedEvent;

import java.time.Year;

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
        AggregateLifecycle.apply(new YearOfFirstReleaseAddedEvent(command.getId(), command.getYearOfFirstRelease()));
    }

    @EventSourcingHandler
    public void in(YearOfFirstReleaseAddedEvent event) {
        this.yearOfFirstRelease = event.getYearOfFirstRelease();
    }


}
