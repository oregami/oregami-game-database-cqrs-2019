package org.oregami.gamingEnvironments.adapter;

import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.axonframework.eventsourcing.DomainEventMessage;
import org.axonframework.eventsourcing.eventstore.DomainEventStream;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.oregami.gamingEnvironments.application.GamingEnvironmentApplicationService;
import org.oregami.gamingEnvironments.model.GamingEnvironmentRepository;
import org.oregami.gamingEnvironments.readmodel.withTitles.RGamingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * Created by sebastian on 17.12.16.
 */
@RequestMapping("/gamingEnvironments")
@Controller
public class GamingEnvironmentResource {

    private GamingEnvironmentApplicationService gamingEnvironmentApplicationService;

    private EventStore eventStore;

    private GamingEnvironmentRepository gamingEnvironmentRepository;

    @Autowired
    public GamingEnvironmentResource(GamingEnvironmentApplicationService gamingEnvironmentApplicationService, EventStore eventStore, GamingEnvironmentRepository gamingEnvironmentRepository) {
        this.gamingEnvironmentApplicationService = gamingEnvironmentApplicationService;
        this.eventStore = eventStore;
        this.gamingEnvironmentRepository = gamingEnvironmentRepository;
    }

    @PostMapping(value = "/create")
    public String create(@RequestParam String workingTitle, Model model) {
        String id = UUID.randomUUID().toString();
        CompletableFuture<Object> completableFuture = gamingEnvironmentApplicationService.createNewGamingEnvironment(id, workingTitle);
        model.addAttribute("gamingEnvironmentId", id);
        return "gamingEnvironments/created";
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("list", gamingEnvironmentRepository.findAll());
        return "gamingEnvironments/list";
    }

    @GetMapping(value = "/{gamingEnvironmentId}")
    public String getOne(@PathVariable String gamingEnvironmentId, Model model) {
        RGamingEnvironment gamingEnvironment = gamingEnvironmentRepository.findById(gamingEnvironmentId).get();
        model.addAttribute("gamingEnvironment", gamingEnvironment);
        model.addAttribute("events", getEventsForGamingEnvironmentAsStrings(gamingEnvironmentId));
        return "gamingEnvironments/one";
    }

    @GetMapping(value = "/create")
    public String createGame(Model model) {
        return "gamingEnvironments/create";
    }


    @GetMapping(value = "/{gamingEnvironmentId}/addYearOfFirstRelease")
    public String addYearOfFirstRelease(@PathVariable String gamingEnvironmentId, Model model) {
        model.addAttribute("gamingEnvironmentId", gamingEnvironmentId);
        return "gamingEnvironments/addYearOfFirstRelease";
    }

    @PostMapping(value = "/{gamingEnvironmentId}/addYearOfFirstRelease")
    public String addYearOfFirstRelease(@PathVariable String gamingEnvironmentId
            , @RequestParam Year yearOfFirstRelease
            , Model model) {
        gamingEnvironmentApplicationService.addYearOfFirstRelease(gamingEnvironmentId, yearOfFirstRelease);
        model.addAttribute("gamingEnvironmentId", gamingEnvironmentId);
        return "gamingEnvironments/update_done";
    }

    private List<Map<String, Object>> getEventsForGamingEnvironmentAsStrings(String gamingEnvironmentId) {
        List<Map<String, Object>> result = new ArrayList<>();
        DomainEventStream domainEventStream = eventStore.readEvents(gamingEnvironmentId);
        Iterator<? extends DomainEventMessage<?>> iterator = domainEventStream.asStream().iterator();
        while (iterator.hasNext()) {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            DomainEventMessage<?> event = iterator.next();
            map.put("Identifier", event.getIdentifier());
            map.put("SequenceNumber", event.getSequenceNumber());

            map.put("Timestamp", event.getTimestamp());
            map.put("Payload", event.getPayloadType().getSimpleName() + ": " + ToStringBuilder.reflectionToString(event.getPayload(), RecursiveToStringStyle.JSON_STYLE));
            map.put("MetaData", ToStringBuilder.reflectionToString(event.getMetaData(), RecursiveToStringStyle.JSON_STYLE));
            result.add(map);
        }
        return result;
    }

}
