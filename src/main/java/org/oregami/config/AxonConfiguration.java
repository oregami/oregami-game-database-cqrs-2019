package org.oregami.config;

import org.springframework.context.annotation.Configuration;

/**
 * Created by sebastian on 26.06.17.
 */
@Configuration
public class AxonConfiguration {



//    @Bean
//    public CorrelationDataProvider correlationDataProvider() {
//        return new CorrelationDataProvider() {
//            @Override
//            public Map<String, ?> correlationDataFor(Message<?> message) {
//                Map<String, Object> m = new HashMap<>();
//                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//                if (authentication!=null && authentication.isAuthenticated()) {
//                    m.put("userId", authentication.getPrincipal());
//                }
//                return m;
//            }
//        };
//    }


}
