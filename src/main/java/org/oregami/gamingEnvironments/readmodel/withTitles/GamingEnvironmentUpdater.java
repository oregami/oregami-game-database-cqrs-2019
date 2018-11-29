package org.oregami.gamingEnvironments.readmodel.withTitles;

import org.axonframework.config.ProcessingGroup;
import org.springframework.stereotype.Component;

/**
 * Created by sebastian on 19.02.17.
 */
@Component
@ProcessingGroup("GamingEnvironmentUpdater")
public class GamingEnvironmentUpdater {

    /*

    @Autowired //TODO use decoupled access (REST call) instead of direct repository call
    TransliteratedStringRepository transliteratedStringRepository;

    GamingEnvironmentRepository repository;

    @Autowired
    public GamingEnvironmentUpdater(GamingEnvironmentRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(GamingEnvironmentCreatedEvent event) {
        RGamingEnvironment g = new RGamingEnvironment(event.getNewId(), event.getWorkingTitle());
        g.setChangeTime(LocalDateTime.now());
        repository.save(g);
    }

    @EventHandler
    public void on(TitleAddedEvent event) {
        RGamingEnvironment g = repository.findOne(event.getGamingEnvironmentId());
        String tsText = transliteratedStringRepository.findOne(event.getTransliteratedStringId()).getText();
        RTitle t = new RTitle(event.getNewTitleId(), event.getTransliteratedStringId(), tsText);
        g.addTitle(t);
        repository.save(g);
    }


    @EventHandler
    public void on(TitleUsageAddedEvent event) {
        RGamingEnvironment g = repository.findOne(event.getGamingEnvironmentId());
        for (RTitle gt: g.getGametitles()) {
            if (gt.getId().equals(event.getTitleId())) {
                RTitleUsage tu = new RTitleUsage(UUID.randomUUID().toString(), event.getRegion(), event.getTitleType());
                gt.getTitleUsages().add(tu);
            }
        }
        repository.save(g);
    }

    */
}
