package org.oregami.gamingEnvironments.adapter;

import org.axonframework.eventsourcing.eventstore.EventStore;
import org.oregami.common.*;
import org.oregami.gamingEnvironments.application.GamingEnvironmentApplicationService;
import org.oregami.gamingEnvironments.application.HardwareModelApplicationService;
import org.oregami.gamingEnvironments.application.HardwarePlatformApplicationService;
import org.oregami.gamingEnvironments.model.GamingEnvironmentRepository;
import org.oregami.gamingEnvironments.model.types.HardwareModelType;
import org.oregami.gamingEnvironments.model.types.HardwarePlatformType;
import org.oregami.gamingEnvironments.readmodel.withTitles.RGamingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by sebastian on 17.12.16.
 */
@RequestMapping("/gamingEnvironments/edit")
@Controller
public class GamingEnvironmentEditResource {

    private GamingEnvironmentApplicationService gamingEnvironmentApplicationService;

    private HardwareModelApplicationService hardwareModelApplicationService;

    private HardwarePlatformApplicationService hardwarePlatformApplicationService;

    private EventStore eventStore;

    private EventHelper eventHelper;

    private GamingEnvironmentRepository gamingEnvironmentRepository;

    @Autowired
    public GamingEnvironmentEditResource(
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
                return "gamingEnvironments/edit/create";
            } else {
                List<CommonError> errors = new ArrayList<>();
                errors.add(new CommonError(new CommonErrorContext("general"), e.getMessage()));
                model.addAttribute("result", new CommonResult<>(errors));
                model.addAttribute("workingTitle", workingTitle);
                return "gamingEnvironments/edit/create";
            }
        }

        model.addAttribute("gamingEnvironmentId", id);
        return "gamingEnvironments/edit/created";
    }

    @GetMapping(value = "/create")
    public String createGame(Model model) {
        return "gamingEnvironments/edit/create";
    }


    @GetMapping(value = "/{gamingEnvironmentId}/addYearOfFirstRelease")
    public String addYearOfFirstRelease(@PathVariable String gamingEnvironmentId, Model model) {
        model.addAttribute("gamingEnvironmentId", gamingEnvironmentId);
        return "gamingEnvironments/edit/addYearOfFirstRelease";
    }

    @PostMapping(value = "/{gamingEnvironmentId}/addYearOfFirstRelease")
    public String addYearOfFirstRelease(@PathVariable String gamingEnvironmentId
            , @RequestParam String yearOfFirstRelease
            , Model model) {

        try {

            Year yearOfFirstReleaseYear = Year.of(Integer.parseInt(yearOfFirstRelease));
            CompletableFuture<Object> completableFuture = gamingEnvironmentApplicationService.addYearOfFirstRelease(gamingEnvironmentId, yearOfFirstReleaseYear);
            Object result = completableFuture.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException | NumberFormatException e) {
            if (e.getCause() instanceof ValidationException) {
                model.addAttribute("result", ((ValidationException) e.getCause()).getResult());
                model.addAttribute("yearOfFirstRelease", yearOfFirstRelease);
                return "gamingEnvironments/edit/addYearOfFirstRelease";
            } else {
                List<CommonError> errors = new ArrayList<>();
                errors.add(new CommonError(new CommonErrorContext("general"), e.getMessage()));
                model.addAttribute("result", new CommonResult<>(errors));
                model.addAttribute("yearOfFirstRelease", yearOfFirstRelease);
                return "gamingEnvironments/edit/addYearOfFirstRelease";
            }
        }

        model.addAttribute("gamingEnvironmentId", gamingEnvironmentId);
        return "gamingEnvironments/edit/updateDone";
    }

    @GetMapping(value = "/{gamingEnvironmentId}/changeYearOfFirstRelease")
    public String changeYearOfFirstRelease(@PathVariable String gamingEnvironmentId, Model model) {
        model.addAttribute("gamingEnvironmentId", gamingEnvironmentId);
        model.addAttribute("yearOfFirstRelease", gamingEnvironmentRepository.getOne(gamingEnvironmentId).getYearOfFirstRelease());
        return "gamingEnvironments/edit/changeYearOfFirstRelease";
    }

    @PostMapping(value = "/{gamingEnvironmentId}/changeYearOfFirstRelease")
    public String changeYearOfFirstRelease(@PathVariable String gamingEnvironmentId
            , @RequestParam Year yearOfFirstRelease
            , Model model) {
        gamingEnvironmentApplicationService.changeYearOfFirstRelease(gamingEnvironmentId, yearOfFirstRelease);
        model.addAttribute("gamingEnvironmentId", gamingEnvironmentId);
        return "gamingEnvironments/edit/updateDone";
    }

    @GetMapping(value = "/{gamingEnvironmentId}/addNewHardwarePlatform")
    public String addNewHardwareplatform(@PathVariable String gamingEnvironmentId, Model model) {
        model.addAttribute("availableHardwarePlatformTypes", getAvailableHardwarePlatformTypes());
        model.addAttribute("gamingEnvironmentId", gamingEnvironmentId);
        model.addAttribute("nextUrl", "/gamingEnvironments/edit/" + gamingEnvironmentId);
        return "hardwarePlatforms/edit/create";
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

    @GetMapping(value = "/{gamingEnvironmentId}/addHardwareModel")
    public String addHardwareModel(
            @PathVariable String gamingEnvironmentId
            , Model model) {
        model.addAttribute("gamingEnvironmentId", gamingEnvironmentId);
        return "gamingEnvironments/edit/createHardwareModel";
    }


    @PostMapping(value = "/{gamingEnvironmentId}/addHardwareModel")
    public String addHardwareModelExecute(
            @PathVariable String gamingEnvironmentId,
            @RequestParam String workingTitle,
            @RequestParam(required = false) String hardwareModelType,
            Model model) {

        model.addAttribute("gamingEnvironmentId", gamingEnvironmentId);

        RGamingEnvironment gamingEnvironment = gamingEnvironmentRepository.findById(gamingEnvironmentId).get();

        String newHardwareModelId = UUID.randomUUID().toString();
        CompletableFuture<Object> completableFuture = hardwareModelApplicationService.createNewHardwareModel(newHardwareModelId, workingTitle, hardwareModelType==null?null:HardwareModelType.valueOf(hardwareModelType));

        try {
            Object result = completableFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {

            if (e.getCause() instanceof ValidationException) {
                model.addAttribute("result", ((ValidationException) e.getCause()).getResult());
                model.addAttribute("workingTitle", workingTitle);
                model.addAttribute("hardwareModelType", hardwareModelType);


                model.addAttribute("gamingEnvironmentId", gamingEnvironmentId);
                return("gamingEnvironments/edit/createHardwareModel");

            } else {
                List<CommonError> errors = new ArrayList<>();
                errors.add(new CommonError(new CommonErrorContext("general"), e.getMessage()));
                model.addAttribute("result", new CommonResult<>(errors));
                model.addAttribute("workingTitle", workingTitle);
                model.addAttribute("gamingEnvironmentId", gamingEnvironmentId);
                model.addAttribute("hardwareModelType", hardwareModelType);
                return("gamingEnvironments/edit/createHardwareModel");

            }
        }

        hardwarePlatformApplicationService.addHardwareModelToHardwarePlatform(gamingEnvironment.getHardwarePlatform().getId(), newHardwareModelId);

        model.addAttribute("gamingEnvironmentId", gamingEnvironmentId);
        return("gamingEnvironments/edit/updateDone");
    }


//    @ExceptionHandler(value = ConversionFailedException.class)
//    public String catchConversionException(HttpServletRequest request, Exception ex) {
//        return "/index";
//    }

    public List<String> getAvailableHardwarePlatformTypes() {
        List<String> list = new ArrayList<>();
        for (HardwarePlatformType h: HardwarePlatformType.values()) {
            list.add(h.name());
        }
        return list;
    }


}
