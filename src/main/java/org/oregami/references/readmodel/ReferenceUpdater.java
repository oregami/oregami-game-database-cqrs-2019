package org.oregami.references.readmodel;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.oregami.references.event.DescriptionAddedEvent;
import org.oregami.references.event.EventIdAddedEvent;
import org.oregami.references.event.ReferenceCreatedEvent;
import org.oregami.references.event.UrlAddedEvent;
import org.oregami.references.model.ReferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@ProcessingGroup("ReferenceUpdater")
public class ReferenceUpdater {


    ReferenceRepository repository;

    @Autowired
    public ReferenceUpdater(ReferenceRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(ReferenceCreatedEvent event) {
        RReference r = new RReference(event.getNewId(), event.getReferenceType().name());
        if (event.getEventIdSet()!=null && !event.getEventIdSet().isEmpty()) {
            for (String id: event.getEventIdSet()) {
                r.addEventId(id);
            }
        }
//        r.setUrl(event.getUrl());
        r.setDescription(event.getDescription());
        r.setChangeTime(LocalDateTime.now());
        repository.save(r);
    }

    @EventHandler
    public void on(UrlAddedEvent event) {
        RReference r = repository.getOne(event.getId());
        r.setUrl(event.getUrl());
        r.setChangeTime(LocalDateTime.now());
        repository.save(r);
    }

    @EventHandler
    public void on(EventIdAddedEvent event) {
        RReference r = repository.getOne(event.getId());
        //r.getEventIdList().add(event.getEventId());
        r.addEventId(event.getEventId());
        r.setChangeTime(LocalDateTime.now());
        repository.save(r);
    }

    @EventHandler
    public void on(DescriptionAddedEvent event) {
        RReference r = repository.getOne(event.getId());
        r.setDescription(event.getDescription());
        r.setChangeTime(LocalDateTime.now());
        repository.save(r);
    }

}
