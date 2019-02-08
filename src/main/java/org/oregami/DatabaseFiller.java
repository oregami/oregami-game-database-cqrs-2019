package org.oregami;

import org.oregami.gamingEnvironments.application.GamingEnvironmentApplicationService;
import org.oregami.gamingEnvironments.application.HardwareModelApplicationService;
import org.oregami.gamingEnvironments.application.HardwarePlatformApplicationService;
import org.oregami.gamingEnvironments.model.HardwarePlatformRepository;
import org.oregami.gamingEnvironments.readmodel.withTitles.RHardwarePlatform;
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

    @Autowired
    private HardwarePlatformApplicationService hardwarePlatformApplicationService;

//    @Autowired
//    private HardwarePlatformRepository hardwarePlatformRepository;

    @Autowired
    private HardwareModelApplicationService hardwareModelApplicationService;


    @Override
    public void run(String... args) throws Exception {

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(getAuth());
        SecurityContextHolder.setContext(context);

        //#########  SNES   #######################
        String snes = UUID.randomUUID().toString();
        gamingEnvironmentApplicationService.createNewGamingEnvironment(snes, "Super Nintendo");
        gamingEnvironmentApplicationService.addYearOfFirstRelease(snes, Year.of(1990));

        String snesHwpId = UUID.randomUUID().toString();
        hardwarePlatformApplicationService.createNewHardwarePlatform(snesHwpId, "SNES console");
        gamingEnvironmentApplicationService.addHardwarePlatformToGamingEnvironment(snes, snesHwpId);

        String snesHWM1Id = UUID.randomUUID().toString();
        String snesHWM2Id = UUID.randomUUID().toString();
        String snesHWM3Id = UUID.randomUUID().toString();

        hardwareModelApplicationService.createNewHardwareModel(snesHWM1Id, "Japanese SHVC-001 model");
        hardwarePlatformApplicationService.addHardwareModelToHardwarePlatform(snesHwpId, snesHWM1Id);

        hardwareModelApplicationService.createNewHardwareModel(snesHWM2Id, "PAL-region SNSP-001A model");
        hardwarePlatformApplicationService.addHardwareModelToHardwarePlatform(snesHwpId, snesHWM2Id);

        hardwareModelApplicationService.createNewHardwareModel(snesHWM3Id, "Japanese SHVC-101 model");
        hardwarePlatformApplicationService.addHardwareModelToHardwarePlatform(snesHwpId, snesHWM3Id);
        /*
        Japanese SHVC-001 model
        PAL-region SNSP-001A model
        Japanese SHVC-101 model
         */


        //#########  C64   #######################
        String c64 = UUID.randomUUID().toString();
        gamingEnvironmentApplicationService.createNewGamingEnvironment(c64, "Commodore 64");
        gamingEnvironmentApplicationService.addYearOfFirstRelease(c64, Year.of(1982));

        String c64HwpId = UUID.randomUUID().toString();
        hardwarePlatformApplicationService.createNewHardwarePlatform(c64HwpId, "Commodore MOS machine & compatibles");
        gamingEnvironmentApplicationService.addHardwarePlatformToGamingEnvironment(c64, c64HwpId);

        String c64HHwmC64OriginalId = UUID.randomUUID().toString();
        String c64HHwmC64_I_Id = UUID.randomUUID().toString();
        String c64HHwmC64_II_Id = UUID.randomUUID().toString();
        String c64HHwmC64_Mini_Id = UUID.randomUUID().toString();

        hardwareModelApplicationService.createNewHardwareModel(c64HHwmC64OriginalId, "original C64");
        hardwarePlatformApplicationService.addHardwareModelToHardwarePlatform(c64HwpId, c64HHwmC64OriginalId);

        hardwareModelApplicationService.createNewHardwareModel(c64HHwmC64_I_Id, "C64-I");
        hardwarePlatformApplicationService.addHardwareModelToHardwarePlatform(c64HwpId, c64HHwmC64_I_Id);

        hardwareModelApplicationService.createNewHardwareModel(c64HHwmC64_II_Id, "C64-II");
        hardwarePlatformApplicationService.addHardwareModelToHardwarePlatform(c64HwpId, c64HHwmC64_II_Id);

        hardwareModelApplicationService.createNewHardwareModel(c64HHwmC64_Mini_Id, "The C64 Mini");
        hardwarePlatformApplicationService.addHardwareModelToHardwarePlatform(c64HwpId, c64HHwmC64_Mini_Id);


        //#########  AMIGA   #######################
        String amiga = UUID.randomUUID().toString();
        gamingEnvironmentApplicationService.createNewGamingEnvironment(amiga, "Commodore Amiga");
        gamingEnvironmentApplicationService.addYearOfFirstRelease(amiga, Year.of(1985));

        String amigaHwpId = UUID.randomUUID().toString();
        hardwarePlatformApplicationService.createNewHardwarePlatform(amigaHwpId, "Amiga 68k machine");
        gamingEnvironmentApplicationService.addHardwarePlatformToGamingEnvironment(amiga, amigaHwpId);

        String amigaHwmAmiga500Id = UUID.randomUUID().toString();
        String amigaHwmAmiga1000Id = UUID.randomUUID().toString();
        String amigaHwmAmiga2000Id = UUID.randomUUID().toString();
        String amigaHwmAmiga1200Id = UUID.randomUUID().toString();
        String amigaHwmAmiga600Id = UUID.randomUUID().toString();
        String amigaHwmAmiga4000Id = UUID.randomUUID().toString();
        String amigaHwmAmiga3000Id = UUID.randomUUID().toString();

        hardwareModelApplicationService.createNewHardwareModel(amigaHwmAmiga500Id, "Amiga 500");
        hardwarePlatformApplicationService.addHardwareModelToHardwarePlatform(amigaHwpId, amigaHwmAmiga500Id);

        hardwareModelApplicationService.createNewHardwareModel(amigaHwmAmiga1000Id, "Amiga 1000");
        hardwarePlatformApplicationService.addHardwareModelToHardwarePlatform(amigaHwpId, amigaHwmAmiga1000Id);

        hardwareModelApplicationService.createNewHardwareModel(amigaHwmAmiga2000Id, "Amiga 2000");
        hardwarePlatformApplicationService.addHardwareModelToHardwarePlatform(amigaHwpId, amigaHwmAmiga2000Id);

        hardwareModelApplicationService.createNewHardwareModel(amigaHwmAmiga1200Id, "Amiga 1200");
        hardwarePlatformApplicationService.addHardwareModelToHardwarePlatform(amigaHwpId, amigaHwmAmiga1200Id);

        hardwareModelApplicationService.createNewHardwareModel(amigaHwmAmiga600Id, "Amiga 600");
        hardwarePlatformApplicationService.addHardwareModelToHardwarePlatform(amigaHwpId, amigaHwmAmiga600Id);

        hardwareModelApplicationService.createNewHardwareModel(amigaHwmAmiga4000Id, "Amiga 4000");
        hardwarePlatformApplicationService.addHardwareModelToHardwarePlatform(amigaHwpId, amigaHwmAmiga4000Id);

        hardwareModelApplicationService.createNewHardwareModel(amigaHwmAmiga3000Id, "Amiga 3000");
        hardwarePlatformApplicationService.addHardwareModelToHardwarePlatform(amigaHwpId, amigaHwmAmiga3000Id);

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