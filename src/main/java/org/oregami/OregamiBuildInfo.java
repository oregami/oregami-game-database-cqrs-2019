package org.oregami;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("OregamiBuildInfo")
public class OregamiBuildInfo {

    @Autowired
    private ApplicationContext context;

    @Value("${application.name}")
    private String applicationName;

    @Value("${build.version}")
    private String buildVersion;

    @Value("${build.timestamp}")
    private String buildTimestamp;

    public String getBuildTimestamp() {
        return buildTimestamp;
    }
}
