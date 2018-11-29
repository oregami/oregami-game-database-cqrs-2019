package org.oregami.gamingEnvironments.adapter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by sebastian on 17.12.16.
 */
@RequestMapping("/gamingEnvironments")
@Controller
public class GamingEnvironmentResource {

    /*
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

    @PostMapping(value = "/{gamingEnvironmentId}/addTitle")
    public String addTitle(@PathVariable String gamingEnvironmentId
            , @RequestParam String titleId
            , Model model) {
        String newTitleId = UUID.randomUUID().toString();
        gamingEnvironmentApplicationService.addTitle(newTitleId, gamingEnvironmentId, titleId);
        model.addAttribute("gamingEnvironmentId", gamingEnvironmentId);
        return "gamingEnvironments/update_done";
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("list", gamingEnvironmentRepository.findAll());
        return "gamingEnvironments/list";
    }

    @GetMapping(value = "/{gamingEnvironmentId}")
    public String getOne(@PathVariable String gamingEnvironmentId, Model model) {
        RGamingEnvironment gamingEnvironment = gamingEnvironmentRepository.findOne(gamingEnvironmentId);
        model.addAttribute("gamingEnvironment", gamingEnvironment);
        model.addAttribute("events", getEventsForGamingEnvironmentAsStrings(gamingEnvironmentId));
        return "gamingEnvironments/one";
    }

    @GetMapping(value = "/create")
    public String createGame(Model model) {
        return "gamingEnvironments/create";
    }

    @GetMapping(value = "/{gamingEnvironmentId}/addTitle")
    public String addTitleForm(
            @PathVariable String gamingEnvironmentId,
            @RequestParam(value = "titleId", defaultValue = "") String titleId,
            Model model) {
        model.addAttribute("gamingEnvironmentId", gamingEnvironmentId);
        model.addAttribute("titleId", titleId);
        return "gamingEnvironments/addTitle";
    }


    @GetMapping(value = "/{gamingEnvironmentId}/editTitleUsage")
    public String titleUsage(@PathVariable String gamingEnvironmentId, Model model) {
        RGamingEnvironment gamingEnvironment = gamingEnvironmentRepository.findOne(gamingEnvironmentId);
        model.addAttribute("gamingEnvironment", gamingEnvironment);

        Region[] possibleRegions = Region.values();
        model.addAttribute("availableRegions", Arrays.asList(possibleRegions));
        TitleType[] possibleTitleTypes = TitleType.values();
        model.addAttribute("availableTitleTypes", Arrays.asList(possibleTitleTypes));

        return "gamingEnvironments/titleUsage/select";
    }


    @PostMapping(value = "/{gamingEnvironmentId}/editTitleUsage/addRegion")
    public String titleUsageAddRegion(@PathVariable String gamingEnvironmentId,
                                      @RequestParam(name="titleId") String titleId,
                                      @RequestParam(name="region") String region,
                                      @RequestParam(name="titleType") String titleType,
                                      Model model) {

        String newTitleUsageId = UUID.randomUUID().toString();
        CompletableFuture<Object> completableFuture = gamingEnvironmentApplicationService.addTitleUsage(newTitleUsageId, gamingEnvironmentId, titleId, Region.valueOf(region), TitleType.valueOf(titleType));

        try {
            Object result = completableFuture.get();

        } catch (InterruptedException e) {
            e.printStackTrace();

        } catch (ExecutionException e) {
            if (e.getCause() instanceof ValidationException) {
                model.addAttribute("result", ((ValidationException) e.getCause()).getResult());
                model.addAttribute("errorRegion", region);

            } else {
                List<CommonError> errors = new ArrayList<>();
                errors.add(new CommonError(new CommonErrorContext("general"), e.getMessage()));
                model.addAttribute("result", new CommonResult<>(errors));
            }
        }


        RGamingEnvironment gamingEnvironment = gamingEnvironmentRepository.findOne(gamingEnvironmentId);
        model.addAttribute("gamingEnvironment", gamingEnvironment);

        Region[] possibleRegions = Region.values();
        model.addAttribute("availableRegions", Arrays.asList(possibleRegions));

        return "gamingEnvironments/titleUsage/select";
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
    */

}
