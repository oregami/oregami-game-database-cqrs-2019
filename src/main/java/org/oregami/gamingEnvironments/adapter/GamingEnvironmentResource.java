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
import org.oregami.gamingEnvironments.event.YearOfFirstReleaseAddedEvent;
import org.oregami.gamingEnvironments.event.YearOfFirstReleaseChangedEvent;
import org.oregami.gamingEnvironments.model.GamingEnvironmentRepository;
import org.oregami.gamingEnvironments.readmodel.withTitles.RGamingEnvironment;
import org.oregami.gamingEnvironments.readmodel.withTitles.RHardwareModel;
import org.oregami.gamingEnvironments.readmodel.withTitles.RHardwarePlatform;
import org.oregami.references.model.ReferenceRepository;
import org.oregami.references.readmodel.RReference;
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
    private ReferenceRepository referenceRepository;

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

        model.addAttribute("aggregateRootIds", getAggregateRootIds(gamingEnvironment));

        //######### read events ##############
        Map<String, Map<String, Object>> eventMap = getEventsForGamingEnvironmentAsStrings(gamingEnvironment);
        model.addAttribute("events", eventMap);

        //######### format events as string ##############
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.PUBLIC_ONLY);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        try {
            String events = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(eventMap);
            model.addAttribute("eventsJson", events);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        List<RReference> all = referenceRepository.findAll();
        try {
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(all));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        //######  references ###################################
        Map<String, RReference> referenceMap = new LinkedHashMap<>();
        for (Map.Entry<String, Map<String, Object>> entry: eventMap.entrySet()) {
            List<RReference> l = referenceRepository.findByEventIdList(entry.getValue().get("Identifier").toString());
            for (RReference r: l) {
                if (referenceMap.get(r.getId())==null) {
                    referenceMap.put(r.getId(), r);
                }
            }
        }
        model.addAttribute("referenceMap", referenceMap);


        return "gamingEnvironments/one";
    }

    private List<String> getAggregateRootIds(RGamingEnvironment gamingEnvironment) {
        List<String> aggregateIdList = new ArrayList<>();
        aggregateIdList.add(gamingEnvironment.getId());

        RHardwarePlatform hwp = gamingEnvironment.getHardwarePlatform();
        if (hwp!=null) {
            aggregateIdList.add(hwp.getId());
            for (RHardwareModel hwm : hwp.getHardwareModelSet()) {
                aggregateIdList.add(hwm.getId());
            }
        }
        return aggregateIdList;
    }

    private Map<String,Map<String, Object>> getEventsForGamingEnvironmentAsStrings(RGamingEnvironment gamingEnvironment) {
        return eventHelper.getEventInformation(getAggregateRootIds(gamingEnvironment));
    }


    private Map<String,Map<String, Object>> filterEvents(String gamingEnvironmentId, String eventQuery) {

        Map<String,Map<String, Object>> allEvents = eventHelper.getEventInformation(gamingEnvironmentId);

        List<String> commandClassList = new ArrayList<>();

        switch (eventQuery) {
            case "yearOfFirstRelease":
                commandClassList.add(YearOfFirstReleaseAddedEvent.class.getName());
                commandClassList.add(YearOfFirstReleaseChangedEvent.class.getName());
                break;
            default:

        }

        Map<String, Map<String, Object>> result = new TreeMap<>();

        if (commandClassList.size()>0) {
            for (String key: allEvents.keySet()) {
                if (commandClassList.contains(allEvents.get(key).get("PayloadType").toString())) {
                    result.put(key, allEvents.get(key));
                }
            }
        }
        return result;
    }


}
