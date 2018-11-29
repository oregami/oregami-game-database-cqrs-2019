package org.oregami.gamingEnvironments.model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateRoot;

/**
 * Created by sebastian on 03.11.16.
 */
@NoArgsConstructor
@AggregateRoot
@EqualsAndHashCode
public class GamingEnvironment {


    @AggregateIdentifier
    private String id;

    String workingTitle;

/*
    @CommandHandler
    public GamingEnvironment(CreateGamingEnvironmentCommand command) {
        AggregateLifecycle.apply(new GamingEnvironmentCreatedEvent(command.getId(), command.getWorkingTitle()));
    }

    @EventSourcingHandler
    public void in(GamingEnvironmentCreatedEvent event) {
        this.id = event.getNewId();
    }

    @CommandHandler
    public String on(AddTitleCommand command) {
        AggregateLifecycle.apply(new TitleAddedEvent(command.getNewId(), command.getGamingEnvironmentId(), command.getTransliteratedStringId()));
        return command.getNewId();
    }

    @EventSourcingHandler
    public void in(TitleAddedEvent event) {
        Title t = new Title(event.getGamingEnvironmentId(), event.getTransliteratedStringId());
        this.gametitles.add(t);
    }

    @CommandHandler
    public String on(AddTitleUsageCommand command) {

        AggregateLifecycle.apply(new TitleUsageAddedEvent(
                command.getGamingEnvironmentId(), //TODO woher die richtige neue ID nehmen?
                command.getTitleId(), command.getRegion(), command.getTitlyType()));
        return command.getNewId();
    }

    @EventSourcingHandler
    public void in(TitleUsageAddedEvent event) {
        for (Title t: this.gametitles) {
            if (t.getTitleId().equals(event.getTitleId())) {
                String newId = UUID.randomUUID().toString();
                System.out.println("newId for TitleUsage: " + newId);
                t.getTitleUsages().add(new TitleUsage(newId, event.getRegion()));
            }
        }

    }

    */

}
