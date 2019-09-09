package org.oregami.home;

import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.eventhandling.DomainEventMessage;
import org.axonframework.eventsourcing.eventstore.DomainEventStream;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.oregami.gamingEnvironments.event.GamingEnvironmentCreatedEvent;
import org.oregami.gamingEnvironments.event.YearOfFirstReleaseAddedEvent;
import org.oregami.gamingEnvironments.event.YearOfFirstReleaseChangedEvent;
import org.oregami.gamingEnvironments.model.GamingEnvironmentRepository;
import org.oregami.gamingEnvironments.readmodel.withTitles.GamingEnvironmentUpdater;
import org.oregami.gamingEnvironments.readmodel.withTitles.RGamingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Controller
@RequestMapping("/replaytools")
public class ReplaytoolsResource {

    @Autowired
    private GamingEnvironmentRepository gamingEnvironmentRepository;

    @Autowired
    private EventStore eventStore;

    @Autowired
    private EventProcessingConfiguration eventProcessingConfiguration;

    @Autowired
    private GamingEnvironmentUpdater gamingEnvironmentUpdater;

	public ReplaytoolsResource() {
	}

    @GetMapping(value = "/removeReadModel/gamingEnvironment/{gamingEnvironmentId}")
    public String removeReadModelGamingEnvironment(@PathVariable String gamingEnvironmentId, Model model) {
        RGamingEnvironment g = gamingEnvironmentRepository.findById(gamingEnvironmentId).get();
        gamingEnvironmentRepository.delete(g);
        return "gamingEnvironments/list";
    }


    @GetMapping(value = "/replayEvents/gamingEnvironment/{gamingEnvironmentId}")
    public String replayEventsGamingEnvironment(@PathVariable String gamingEnvironmentId, Model model) {

        DomainEventStream domainEventStream = eventStore.readEvents(gamingEnvironmentId);
        Iterator<? extends DomainEventMessage<?>> iterator = domainEventStream.asStream().iterator();
        List<DomainEventMessage<?>> eventList = new ArrayList<>();
        while (iterator.hasNext()) {
            DomainEventMessage<?> domainEventMessage = iterator.next();
            eventList.add(domainEventMessage);
            if (domainEventMessage.getPayload() instanceof GamingEnvironmentCreatedEvent) {
                gamingEnvironmentUpdater.on((GamingEnvironmentCreatedEvent) domainEventMessage.getPayload());
            }
            else if (domainEventMessage.getPayload() instanceof YearOfFirstReleaseAddedEvent) {
                gamingEnvironmentUpdater.on((YearOfFirstReleaseAddedEvent) domainEventMessage.getPayload());
            } else if (domainEventMessage.getPayload() instanceof YearOfFirstReleaseChangedEvent) {
                gamingEnvironmentUpdater.on((YearOfFirstReleaseChangedEvent) domainEventMessage.getPayload());
            }
            //TODO more events, HardwarePlatforms & HardwareModels
        }
        return "gamingEnvironments/list";

        /*
        if (gamingEnvironmentId.equals("rebuild")) {
            eventProcessingConfiguration
                    .eventProcessorByProcessingGroup("GamingEnvironmentUpdater",
                            TrackingEventProcessor.class)
                    .ifPresent(trackingEventProcessor -> {
                        trackingEventProcessor.shutDown();
                        trackingEventProcessor.resetTokens(); // (1)
                        trackingEventProcessor.start();
                    });
            return "gamingEnvironments/list";
        }
         */

    }
}
