package org.oregami.gamingEnvironments.adapter;

import org.axonframework.eventsourcing.eventstore.EventStore;
import org.oregami.common.*;
import org.oregami.gamingEnvironments.application.HardwarePlatformApplicationService;
import org.oregami.gamingEnvironments.model.HardwarePlatformRepository;
import org.oregami.gamingEnvironments.model.types.HardwarePlatformType;
import org.oregami.gamingEnvironments.readmodel.withTitles.RHardwarePlatform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by sebastian on 17.12.16.
 */
@RequestMapping("/hardwarePlatforms/edit")
@Controller
public class HardwarePlatformEditResource {

    private HardwarePlatformApplicationService hardwarePlatformApplicationService;

    private EventStore eventStore;

    private EventHelper eventHelper;

    private HardwarePlatformRepository hardwarePlatformRepository;

    @Autowired
    public HardwarePlatformEditResource(
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
        return "hardwarePlatforms/edit/create";
    }


    @PostMapping(value = "/create")
    public String create(@RequestParam String workingTitle
            , Model model
            , @RequestParam(required = false) String hardwarePlatformType
            , @RequestParam(required = false) String gamingEnvironmentId
            , @RequestParam(value = "nextUrl", defaultValue = "") String nextUrl
                         ) {
        String id = UUID.randomUUID().toString();
        CompletableFuture<Object> completableFuture = hardwarePlatformApplicationService.createNewHardwarePlatform(id, workingTitle, StringUtils.isEmpty(hardwarePlatformType)?null: HardwarePlatformType.valueOf(hardwarePlatformType));
        model.addAttribute("nextUrl", nextUrl);

        try {
            Object result = completableFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {

            if (e.getCause() instanceof ValidationException) {
                model.addAttribute("result", ((ValidationException) e.getCause()).getResult());
                model.addAttribute("workingTitle", workingTitle);
                model.addAttribute("hardwarePlatformType", hardwarePlatformType);
                return "hardwarePlatforms/edit/create";
            } else {
                List<CommonError> errors = new ArrayList<>();
                errors.add(new CommonError(new CommonErrorContext("general"), e.getMessage()));
                model.addAttribute("result", new CommonResult<>(errors));
                model.addAttribute("workingTitle", workingTitle);
                model.addAttribute("hardwarePlatformType", hardwarePlatformType);

                return "gamingEnvironments/edit/" + gamingEnvironmentId + "/addNewHardwarePlatform";
            }
        }

        model.addAttribute("hardwarePlatformId", id);

        return "hardwarePlatforms/edit/created";
    }

    @GetMapping(value = "/{hardwarePlatformId}/addNewHardwareModel")
    public String addNewHardwareModel(
            @PathVariable String hardwarePlatformId,
            @RequestParam String gamingEnvironmentId,
            Model model) {
        model.addAttribute("hardwarePlatformId", hardwarePlatformId);
        model.addAttribute("nextUrl", "/gamingEnvironments/" + gamingEnvironmentId);
        return "hardwareModels/edit/create";
    }

    public List<String> getAvailableHardwarePlatformTypes() {
        List<String> list = new ArrayList<>();
        for (HardwarePlatformType h: HardwarePlatformType.values()) {
            list.add(h.name());
        }
        return list;
    }

}
