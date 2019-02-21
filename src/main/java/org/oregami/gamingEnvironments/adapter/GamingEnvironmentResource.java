package org.oregami.gamingEnvironments.adapter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.oregami.common.EventHelper;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

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

    @GetMapping
    public String getAll(Model model) {
        List<RGamingEnvironment> list = gamingEnvironmentRepository.findAll();
        model.addAttribute("list", list);


        Map<String, List<RGamingEnvironment>> gamingEnvironmentMap = new TreeMap<>();
        for (RGamingEnvironment g: list) {
            if (g.getHardwarePlatform()!=null && g.getHardwarePlatform().getHardwarePlatformType()!=null) {
                String type = g.getHardwarePlatform().getHardwarePlatformType();
                if (gamingEnvironmentMap.get(type)==null) {
                    gamingEnvironmentMap.put(type, new ArrayList<>());
                }
                gamingEnvironmentMap.get(type).add(g);
            } else {
                String type = "unknown";
                if (gamingEnvironmentMap.get(type)==null) {
                    gamingEnvironmentMap.put(type, new ArrayList<>());
                }
                gamingEnvironmentMap.get(type).add(g);
            }

        }
        model.addAttribute("gamingEnvironmentMap", gamingEnvironmentMap);
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


}
