package org.oregami;

import org.oregami.gamingEnvironments.application.GamingEnvironmentApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.Year;
import java.util.Collection;
import java.util.UUID;

@Component
public class DatabaseFiller implements CommandLineRunner {

    @Autowired
    private GamingEnvironmentApplicationService gamingEnvironmentApplicationService;


    @Override
    public void run(String... args) throws Exception {

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(getAuth());
        SecurityContextHolder.setContext(context);

        String c64 = UUID.randomUUID().toString();
        gamingEnvironmentApplicationService.createNewGamingEnvironment(c64, "Commodore 64");
        gamingEnvironmentApplicationService.addYearOfFirstRelease(c64, Year.of(1982));


        String amiga = UUID.randomUUID().toString();
        gamingEnvironmentApplicationService.createNewGamingEnvironment(amiga, "Commodore Amiga");
        gamingEnvironmentApplicationService.addYearOfFirstRelease(amiga, Year.of(1985));

    }


    private Authentication getAuth() {
        return  new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return "init-user-id";
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }

            @Override
            public void setAuthenticated(boolean b) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return "init-user-name";
            }
        };
    }

}