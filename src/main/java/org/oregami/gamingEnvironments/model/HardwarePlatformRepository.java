package org.oregami.gamingEnvironments.model;

import org.oregami.gamingEnvironments.readmodel.withTitles.RGamingEnvironment;
import org.oregami.gamingEnvironments.readmodel.withTitles.RHardwarePlatform;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by sebastian on 19.02.17.
 */
public interface HardwarePlatformRepository extends JpaRepository<RHardwarePlatform, String> {
}
