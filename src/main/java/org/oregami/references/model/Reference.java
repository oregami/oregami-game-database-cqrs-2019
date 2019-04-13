package org.oregami.references.model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.oregami.references.command.AddDescriptionCommand;
import org.oregami.references.command.AddEventIdCommand;
import org.oregami.references.command.AddUrlCommand;
import org.oregami.references.command.CreateReferenceCommand;
import org.oregami.references.event.DescriptionAddedEvent;
import org.oregami.references.event.EventIdAddedEvent;
import org.oregami.references.event.ReferenceCreatedEvent;
import org.oregami.references.event.UrlAddedEvent;
import org.oregami.references.model.types.ReferenceType;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Aggregate
@EqualsAndHashCode
public class Reference {

    @AggregateIdentifier
    private String id;

    private ReferenceType referenceType;

    private String url;

    private String description;

    private List<String> eventIdList = new ArrayList<>();


    @CommandHandler
    public Reference(CreateReferenceCommand command) {
        AggregateLifecycle.apply(new ReferenceCreatedEvent(command.getNewId(), command.getReferenceType(), command.getEventIdSet(), command.getUrl(), command.getDescription()));
    }

    @EventSourcingHandler
    public void in(ReferenceCreatedEvent event) {
        this.id = event.getNewId();
        this.referenceType = event.getReferenceType();
    }


    @CommandHandler
    public void in(AddUrlCommand command) {
        AggregateLifecycle.apply(new UrlAddedEvent(command.getReferenceId(), command.getUrl()));
    }

    @EventSourcingHandler
    public void in(UrlAddedEvent event) {
        this.url = event.getUrl();
    }

    @CommandHandler
    public void in(AddEventIdCommand command) {
        AggregateLifecycle.apply(new EventIdAddedEvent(command.getReferenceId(), command.getEventId()));
    }

    @EventSourcingHandler
    public void in(EventIdAddedEvent event) {
        eventIdList.add(event.getEventId());
    }

    @CommandHandler
    public void in(AddDescriptionCommand command) {
        AggregateLifecycle.apply(new DescriptionAddedEvent(command.getReferenceId(), command.getDescription()));
    }

    @EventSourcingHandler
    public void in(DescriptionAddedEvent event) {
        this.url = event.getDescription();
    }

}
