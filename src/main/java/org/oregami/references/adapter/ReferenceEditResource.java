package org.oregami.references.adapter;

import org.oregami.common.EventHelper;
import org.oregami.references.application.ReferenceApplicationService;
import org.oregami.references.model.types.ReferenceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RequestMapping("/references/edit")
@Controller
public class ReferenceEditResource {

    private EventHelper eventHelper;

    private ReferenceApplicationService referenceApplicationService;

    @Autowired
    public ReferenceEditResource(
            EventHelper eventHelper,
            ReferenceApplicationService referenceApplicationService
    ) {
        this.eventHelper = eventHelper;
        this.referenceApplicationService = referenceApplicationService;
    }


    @GetMapping(value = "/addNewReference")
    public String addNewReference(
            @RequestParam String aggregateRootId,
            //@RequestParam(required = false) List<String> selectedEventIds,
            Model model) {

        model.addAttribute("aggregateRootId", aggregateRootId);
        model.addAttribute("step", 1);
        model.addAttribute("events", eventHelper.getEventInformation(aggregateRootId));

        return "references/edit/addNewReference";
    }


    @PostMapping(value = "/addNewReference")
    public String addNewReferenceStep2(
            @RequestParam String aggregateRootId,
            @RequestParam(required = false) String selectedEventIds,
            Model model) {

//        if (selectedEventIds==null || selectedEventIds.length==0) {
//            model.addAttribute("error", "no events selected");
//        } else {
            model.addAttribute("step", 2);
            model.addAttribute("selectedEvents", eventHelper.getEventsWithIds(aggregateRootId, Arrays.asList(selectedEventIds.split(","))));
//        }
        model.addAttribute("aggregateRootId", aggregateRootId);
        model.addAttribute("selectedEventIds", selectedEventIds);
        model.addAttribute("availableReferenceTypes", getAvailableReferenceTypes());

        //model.addAttribute("events", eventHelper.getEventInformation(aggregateRootId));

        return "references/edit/addNewReference";
    }


    @PostMapping(value = "/saveNewReference")
    public RedirectView saveNewReference(
            @RequestParam String aggregateRootId,
            @RequestParam String referenceType,
            @RequestParam String selectedEventIds,
            @RequestParam (required = false) String url,
            @RequestParam (required = false) String description,
            Model model) {

        String id = UUID.randomUUID().toString();
        CompletableFuture<Object> completableFuture = referenceApplicationService.createNewReference(
                id, ReferenceType.valueOf(referenceType), new TreeSet<>(Arrays.asList(selectedEventIds.split(","))), url, description
        );

        try {
            Object o = completableFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        model.addAttribute("aggregateRootId", aggregateRootId);
        model.addAttribute("selectedEventIds", selectedEventIds);
        model.addAttribute("availableReferenceTypes", getAvailableReferenceTypes());

        //return "references/edit/addNewReference";
        RedirectView rv = new RedirectView();
        rv.setUrl("/gamingEnvironments/" + aggregateRootId);
        return rv;
    }


    public List<String> getAvailableReferenceTypes() {
        List<String> list = new ArrayList<>();
        for (ReferenceType t: ReferenceType.values()) {
            list.add(t.name());
        }
        return list;
    }
}
