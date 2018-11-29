package org.oregami.config;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = KeycloakSecurityComponents.class)
public class SecurityConfiguration extends KeycloakWebSecurityConfigurerAdapter {

    @Value("${keycloak.auth-server-url}")
    private String keycloakUrl;

    public static final String USER_ROLE = "user";

    /**
     * Registers the KeycloakAuthenticationProvider with the authentication manager.
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
        auth.authenticationProvider(keycloakAuthenticationProvider);
    }

    @Bean
    public KeycloakConfigResolver KeycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }

    /**
     * Defines the session authentication strategy.
     */
    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.info("keycloakUrl: " + keycloakUrl);

        super.configure(http);
        http.csrf().disable()

                .headers().frameOptions().disable();

        http.authorizeRequests()

                //.antMatchers(HttpMethod.GET, "/").authenticated()

                .antMatchers(HttpMethod.GET, "/login").fullyAuthenticated()

                //.antMatchers(HttpMethod.GET, "/**/games/createGame").hasRole(userRole)
                .antMatchers(HttpMethod.POST, "/**/games/createGame").hasRole(USER_ROLE)


                //.antMatchers(HttpMethod.GET, "/**/gamingEnvironments/create").hasRole(userRole)
                .antMatchers(HttpMethod.POST, "/**/gamingEnvironments/create").hasRole(USER_ROLE)


                //.antMatchers(HttpMethod.GET, "/**/transliteratedStrings/create").hasRole(userRole)
                .antMatchers(HttpMethod.POST, "/**/transliteratedStrings/create").hasRole(USER_ROLE)

                //.antMatchers(HttpMethod.GET, "/**/addTitle").hasRole(userRole)
                .antMatchers(HttpMethod.POST, "/**/addTitle").hasRole(USER_ROLE)
                .anyRequest().permitAll();
    }
}