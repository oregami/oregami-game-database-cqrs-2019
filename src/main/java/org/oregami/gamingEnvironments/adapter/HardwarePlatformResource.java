package org.oregami.gamingEnvironments.adapter;

import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.axonframework.eventsourcing.DomainEventMessage;
import org.axonframework.eventsourcing.eventstore.DomainEventStream;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.oregami.common.EventHelper;
import org.oregami.gamingEnvironments.application.HardwarePlatformApplicationService;
import org.oregami.gamingEnvironments.model.HardwarePlatformRepository;
import org.oregami.gamingEnvironments.readmodel.withTitles.RGamingEnvironment;
import org.oregami.gamingEnvironments.readmodel.withTitles.RHardwarePlatform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * Created by sebastian on 17.12.16.
 */
@RequestMapping("/hardwarePlatforms")
@Controller
public class HardwarePlatformResource {

    private HardwarePlatformApplicationService hardwarePlatformApplicationService;

    private EventStore eventStore;

    private EventHelper eventHelper;

    private HardwarePlatformRepository hardwarePlatformRepository;

    @Autowired
    public HardwarePlatformResource(
            HardwarePlatformApplicationService hardwarePlatformApplicationService,
            EventStore eventStore,
            EventHelper eventHelper,
            HardwarePlatformRepository hardwarePlatformRepository
    ) {
        this.hardwarePlatformApplicationService = hardwarePlatformApplicationService;
        this.eventStore = eventStore;
        this.eventHelper = eventHelper;
        this.hardwarePlatformRepository = hardwarePlatformRepository;
    }

    @GetMapping(value = "/create")
    public String create(Model model) {
        return "hardwarePlatforms/create";
    }


    @PostMapping(value = "/create")
    public String create(@RequestParam String workingTitle
            , Model model
            , @RequestParam(required = false) String gamingEnvironmentId
            , @RequestParam(value = "nextUrl", defaultValue = "") String nextUrl
                         ) {
        String id = UUID.randomUUID().toString();
        CompletableFuture<Object> completableFuture = hardwarePlatformApplicationService.createNewHardwarePlatform(id, workingTitle);
        model.addAttribute("hardwarePlatformId", id);
        model.addAttribute("nextUrl", nextUrl);

        return "hardwarePlatforms/created";
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("list", hardwarePlatformRepository.findAll());
        return "hardwarePlatforms/list";
    }

    @GetMapping(value = "/{hardwarePlatformId}")
    public String getOne(@PathVariable String hardwarePlatformId, Model model) {
        RHardwarePlatform hardwarePlatform = hardwarePlatformRepository.findById(hardwarePlatformId).get();
        model.addAttribute("hardwarePlatform", hardwarePlatform);
        model.addAttribute("events", eventHelper.getEventInformation(hardwarePlatformId));
        return "hardwarePlatforms/one";
    }

    @GetMapping(value = "/{hardwarePlatformId}/addNewHardwareModel")
    public String addNewHardwareModel(
            @PathVariable String hardwarePlatformId,
            @RequestParam String gamingEnvironmentId,
            Model model) {
        model.addAttribute("hardwarePlatformId", hardwarePlatformId);
        model.addAttribute("nextUrl", "/gamingEnvironments/" + gamingEnvironmentId);
        return "hardwareModels/create";
    }
}
