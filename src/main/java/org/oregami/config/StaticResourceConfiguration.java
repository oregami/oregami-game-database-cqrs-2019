package org.oregami.config;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@Component
public class StaticResourceConfiguration implements WebMvcConfigurer, HandlerInterceptor {

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/", "file:" + "/www/" };

    private URLConfiguration urlConfiguration;

    @Autowired
    private LocaleChangeInterceptor localeChangeInterceptor;

    @Autowired
    public StaticResourceConfiguration(URLConfiguration urlConfiguration) {
        this.urlConfiguration = urlConfiguration;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/ext/**", "/static/**")
                .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);

    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //System.out.println("addInterceptors aufgerufen!");
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                return true;
            }

            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
                for (URLConfiguration.URLS url : urlConfiguration.getUrls()) {
                    if (modelAndView != null) {
                        modelAndView.addObject("URL_" + url.name(), url.value);
                    }
                }

                Object userPrincipalObject = request.getUserPrincipal();
                if (modelAndView!=null && userPrincipalObject !=null && userPrincipalObject instanceof KeycloakAuthenticationToken) {
                    KeycloakAuthenticationToken userPrincipal = (KeycloakAuthenticationToken) userPrincipalObject;
                    modelAndView.addObject("loggedIn", true);
                    modelAndView.addObject("userId", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
                    if (userPrincipal!=null) {
                        modelAndView.addObject("userName", userPrincipal.getAccount().getKeycloakSecurityContext().getIdToken().getPreferredUsername());
                    }

                } else {
                    if (modelAndView!=null) {
                        modelAndView.addObject("loggedIn", false);
                    }
                }
                //System.out.println("postHandle aufgerufen!");
            }

            @Override
            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
            }
        });
        registry.addInterceptor(localeChangeInterceptor);
    }


    public URLConfiguration getUrlConfiguration() {
        return urlConfiguration;
    }

    public void setUrlConfiguration(URLConfiguration urlConfiguration) {
        this.urlConfiguration = urlConfiguration;
    }
}
