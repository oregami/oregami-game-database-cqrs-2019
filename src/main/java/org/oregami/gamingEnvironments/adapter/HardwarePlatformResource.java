package org.oregami.gamingEnvironments.adapter;

import org.axonframework.eventsourcing.eventstore.EventStore;
import org.oregami.common.EventHelper;
import org.oregami.gamingEnvironments.application.HardwarePlatformApplicationService;
import org.oregami.gamingEnvironments.model.HardwarePlatformRepository;
import org.oregami.gamingEnvironments.readmodel.withTitles.RHardwarePlatform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
