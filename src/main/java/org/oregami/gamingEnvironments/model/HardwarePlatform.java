package org.oregami.gamingEnvironments.model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.oregami.gamingEnvironments.command.CreateHardwarePlatformCommand;
import org.oregami.gamingEnvironments.event.HardwarePlatformCreatedEvent;

/**
 * Created by sebastian on 03.11.16.
 */
@NoArgsConstructor
@Aggregate
@EqualsAndHashCode
public class HardwarePlatform {


    @AggregateIdentifier
    private String id;

    String workingTitle;


    @CommandHandler
    public HardwarePlatform(CreateHardwarePlatformCommand command) {
        AggregateLifecycle.apply(new HardwarePlatformCreatedEvent(command.getId(), command.getWorkingTitle()));
    }

    @EventSourcingHandler
    public void in(HardwarePlatformCreatedEvent event) {
        this.id = event.getNewId();
    }


}
