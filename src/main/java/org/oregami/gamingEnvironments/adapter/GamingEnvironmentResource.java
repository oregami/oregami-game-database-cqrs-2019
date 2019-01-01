package org.oregami.gamingEnvironments.adapter;

import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.axonframework.eventsourcing.DomainEventMessage;
import org.axonframework.eventsourcing.eventstore.DomainEventStream;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.oregami.common.EventHelper;
import org.oregami.gamingEnvironments.application.GamingEnvironmentApplicationService;
import org.oregami.gamingEnvironments.model.GamingEnvironmentRepository;
import org.oregami.gamingEnvironments.readmodel.withTitles.RGamingEnvironment;
import org.oregami.gamingEnvironments.readmodel.withTitles.RHardwarePlatform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.Year;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by sebastian on 17.12.16.
 */
@RequestMapping("/gamingEnvironments")
@Controller
public class GamingEnvironmentResource {

    private GamingEnvironmentApplicationService gamingEnvironmentApplicationService;

    private EventStore eventStore;

    private EventHelper eventHelper;

    private GamingEnvironmentRepository gamingEnvironmentRepository;

    @Autowired
    public GamingEnvironmentResource(
            GamingEnvironmentApplicationService gamingEnvironmentApplicationService,
            EventStore eventStore,
            EventHelper eventHelper,
            GamingEnvironmentRepository gamingEnvironmentRepository
    ) {
        this.gamingEnvironmentApplicationService = gamingEnvironmentApplicationService;
        this.eventStore = eventStore;
        this.gamingEnvironmentRepository = gamingEnvironmentRepository;
        this.eventHelper = eventHelper;
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
        model.addAttribute("events", getEventsForGamingEnvironmentAsStrings(gamingEnvironment));
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

    @GetMapping(value = "/{gamingEnvironmentId}/changeYearOfFirstRelease")
    public String changeYearOfFirstRelease(@PathVariable String gamingEnvironmentId, Model model) {
        model.addAttribute("gamingEnvironmentId", gamingEnvironmentId);
        model.addAttribute("yearOfFirstRelease", gamingEnvironmentRepository.getOne(gamingEnvironmentId).getYearOfFirstRelease());
        return "gamingEnvironments/changeYearOfFirstRelease";
    }

    @PostMapping(value = "/{gamingEnvironmentId}/changeYearOfFirstRelease")
    public String changeYearOfFirstRelease(@PathVariable String gamingEnvironmentId
            , @RequestParam Year yearOfFirstRelease
            , Model model) {
        gamingEnvironmentApplicationService.changeYearOfFirstRelease(gamingEnvironmentId, yearOfFirstRelease);
        model.addAttribute("gamingEnvironmentId", gamingEnvironmentId);
        return "gamingEnvironments/update_done";
    }

    @GetMapping(value = "/{gamingEnvironmentId}/addNewHardwarePlatform")
    public String addNewHardwareplatform(@PathVariable String gamingEnvironmentId, Model model) {
        model.addAttribute("gamingEnvironmentId", gamingEnvironmentId);
        model.addAttribute("nextUrl", "/gamingEnvironments/" + gamingEnvironmentId);
        return "hardwarePlatforms/create";
    }

    @GetMapping(value = "/{gamingEnvironmentId}/addHardwarePlatform")
    public RedirectView addHardwareplatform(
            @PathVariable String gamingEnvironmentId
            , @RequestParam String hardwarePlatformId
            , Model model) {
        CompletableFuture<Object> completableFuture = gamingEnvironmentApplicationService.addHardwarePlatformToGamingEnvironment(gamingEnvironmentId, hardwarePlatformId);
        try {
            completableFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        RedirectView rv = new RedirectView();
        rv.setUrl("/gamingEnvironments/" + gamingEnvironmentId);
        return rv;
    }

    private Map<Instant,Map<String, Object>> getEventsForGamingEnvironmentAsStrings(RGamingEnvironment gamingEnvironment) {
        Map<Instant, Map<String, Object>> result = new TreeMap<>();
        result.putAll(eventHelper.getEventInformation(gamingEnvironment.getId()));

        for (RHardwarePlatform h: gamingEnvironment.getHardwarePlatformSet()) {
            result.putAll(eventHelper.getEventInformation(h.getId()));
        }
        return result;
    }




}
