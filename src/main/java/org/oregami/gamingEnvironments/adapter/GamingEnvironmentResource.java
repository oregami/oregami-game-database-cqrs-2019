package org.oregami.gamingEnvironments.adapter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.oregami.common.*;
import org.oregami.gamingEnvironments.application.GamingEnvironmentApplicationService;
import org.oregami.gamingEnvironments.application.HardwareModelApplicationService;
import org.oregami.gamingEnvironments.application.HardwarePlatformApplicationService;
import org.oregami.gamingEnvironments.model.GamingEnvironmentRepository;
import org.oregami.gamingEnvironments.readmodel.withTitles.RGamingEnvironment;
import org.oregami.gamingEnvironments.readmodel.withTitles.RHardwareModel;
import org.oregami.gamingEnvironments.readmodel.withTitles.RHardwarePlatform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

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

    private HardwareModelApplicationService hardwareModelApplicationService;

    private HardwarePlatformApplicationService hardwarePlatformApplicationService;

    private EventStore eventStore;

    private EventHelper eventHelper;

    private GamingEnvironmentRepository gamingEnvironmentRepository;

    @Autowired
    public GamingEnvironmentResource(
            GamingEnvironmentApplicationService gamingEnvironmentApplicationService,
            HardwarePlatformApplicationService hardwarePlatformApplicationService,
            HardwareModelApplicationService hardwareModelApplicationService,
            EventStore eventStore,
            EventHelper eventHelper,
            GamingEnvironmentRepository gamingEnvironmentRepository
    ) {
        this.gamingEnvironmentApplicationService = gamingEnvironmentApplicationService;
        this.hardwarePlatformApplicationService = hardwarePlatformApplicationService;
        this.hardwareModelApplicationService = hardwareModelApplicationService;
        this.eventStore = eventStore;
        this.gamingEnvironmentRepository = gamingEnvironmentRepository;
        this.eventHelper = eventHelper;
    }

    @PostMapping(value = "/create")
    public String create(@RequestParam String workingTitle, Model model) {
        String id = UUID.randomUUID().toString();
        CompletableFuture<Object> completableFuture = gamingEnvironmentApplicationService.createNewGamingEnvironment(id, workingTitle);

        try {
            Object result = completableFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {

            if (e.getCause() instanceof ValidationException) {
                model.addAttribute("result", ((ValidationException) e.getCause()).getResult());
                model.addAttribute("workingTitle", workingTitle);
                return "gamingEnvironments/create";
            } else {
                List<CommonError> errors = new ArrayList<>();
                errors.add(new CommonError(new CommonErrorContext("general"), e.getMessage()));
                model.addAttribute("result", new CommonResult<>(errors));
                model.addAttribute("workingTitle", workingTitle);
                return "gamingEnvironments/create";
            }
        }

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

        Map<String, Map<String, Object>> eventMap = getEventsForGamingEnvironmentAsStrings(gamingEnvironment);
        model.addAttribute("events", eventMap);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.PUBLIC_ONLY);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        try {
            String events = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(eventMap);
            model.addAttribute("eventsJson", events);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }



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
        return "gamingEnvironments/updateDone";
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
        return "gamingEnvironments/updateDone";
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

    private Map<String,Map<String, Object>> getEventsForGamingEnvironmentAsStrings(RGamingEnvironment gamingEnvironment) {
        Map<String, Map<String, Object>> result = new TreeMap<>();
        result.putAll(eventHelper.getEventInformation(gamingEnvironment.getId()));

        RHardwarePlatform hwp = gamingEnvironment.getHardwarePlatform();
        if (hwp!=null) {
            result.putAll(eventHelper.getEventInformation(hwp.getId()));
            for (RHardwareModel hwm : hwp.getHardwareModelSet()) {
                result.putAll(eventHelper.getEventInformation(hwm.getId()));
            }
        }
        return result;
    }



    @GetMapping(value = "/{gamingEnvironmentId}/addHardwareModel")
    public String addHardwareModel(
            @PathVariable String gamingEnvironmentId
            , Model model) {
        model.addAttribute("gamingEnvironmentId", gamingEnvironmentId);
        return "gamingEnvironments/createHardwareModel";
    }


    @PostMapping(value = "/{gamingEnvironmentId}/addHardwareModel")
    public String addHardwareModelExecute(
            @PathVariable String gamingEnvironmentId,
            @RequestParam String workingTitle,
            Model model) {

        model.addAttribute("gamingEnvironmentId", gamingEnvironmentId);

        RGamingEnvironment gamingEnvironment = gamingEnvironmentRepository.findById(gamingEnvironmentId).get();

        String newHardwareModelId = UUID.randomUUID().toString();
        CompletableFuture<Object> completableFuture = hardwareModelApplicationService.createNewHardwareModel(newHardwareModelId, workingTitle);

        try {
            Object result = completableFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {

            if (e.getCause() instanceof ValidationException) {
                model.addAttribute("result", ((ValidationException) e.getCause()).getResult());
                model.addAttribute("workingTitle", workingTitle);

                model.addAttribute("gamingEnvironmentId", gamingEnvironmentId);
                return("/gamingEnvironments/createHardwareModel");

            } else {
                List<CommonError> errors = new ArrayList<>();
                errors.add(new CommonError(new CommonErrorContext("general"), e.getMessage()));
                model.addAttribute("result", new CommonResult<>(errors));
                model.addAttribute("workingTitle", workingTitle);
                model.addAttribute("gamingEnvironmentId", gamingEnvironmentId);
                return("/gamingEnvironments/createHardwareModel");

            }
        }

        hardwarePlatformApplicationService.addHardwareModelToHardwarePlatform(gamingEnvironment.getHardwarePlatform().getId(), newHardwareModelId);

        model.addAttribute("gamingEnvironmentId", gamingEnvironmentId);
        return("gamingEnvironments/updateDone");
    }


}
