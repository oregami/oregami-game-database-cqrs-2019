package org.oregami.gamingEnvironments.model;

import org.oregami.gamingEnvironments.readmodel.withTitles.RGamingEnvironment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by sebastian on 19.02.17.
 */
public interface GamingEnvironmentRepository extends JpaRepository<RGamingEnvironment, String> {
}
