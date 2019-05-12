package org.oregami;

import org.oregami.common.EventHelper;
import org.oregami.gamingEnvironments.application.GamingEnvironmentApplicationService;
import org.oregami.gamingEnvironments.application.HardwareModelApplicationService;
import org.oregami.gamingEnvironments.application.HardwarePlatformApplicationService;
import org.oregami.gamingEnvironments.model.types.HardwareModelType;
import org.oregami.gamingEnvironments.model.types.HardwarePlatformType;
import org.oregami.references.application.ReferenceApplicationService;
import org.oregami.references.model.types.ReferenceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.Year;
import java.util.*;

@Component
public class DatabaseFiller implements CommandLineRunner {

    @Autowired
    private GamingEnvironmentApplicationService gamingEnvironmentApplicationService;

    @Autowired
    private HardwarePlatformApplicationService hardwarePlatformApplicationService;

    @Autowired
    private HardwareModelApplicationService hardwareModelApplicationService;

    @Autowired
    private EventHelper eventHelper;

    @Autowired
    private ReferenceApplicationService referenceApplicationService;


    @Override
    public void run(String... args) throws Exception {

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(getAuth());
        SecurityContextHolder.setContext(context);

        //#########  SNES   #######################
        String snes = UUID.randomUUID().toString();
        gamingEnvironmentApplicationService.createNewGamingEnvironment(snes, "Super Nintendo").get();


        String refId1 = UUID.randomUUID().toString();
        //String refId2 = UUID.randomUUID().toString();

        Set<String> eventIdSet = new HashSet<>();
        referenceApplicationService.createNewReference(refId1, ReferenceType.FAN_SITE, eventIdSet, null, null).get();
        referenceApplicationService.addUrl(refId1, "https://nintendo.fandom.com/wiki/Super_Famicom");

        Map<String, Map<String, Object>> eventInformationSnes = eventHelper.getEventInformation(snes);
        for (String key: eventInformationSnes.keySet()) {
            referenceApplicationService.addEventId(refId1, eventInformationSnes.get(key).get("Identifier").toString());
            //referenceApplicationService.addEventId(refId2, eventInformationSnes.get(key).get("Identifier").toString());
        }

        gamingEnvironmentApplicationService.addYearOfFirstRelease(snes, Year.of(1990)).get();


        String snesHwpId = UUID.randomUUID().toString();
        hardwarePlatformApplicationService.createNewHardwarePlatform(snesHwpId, "SNES console", HardwarePlatformType.CONSOLES_EUROPE_NORTHAMERICA).get();
        gamingEnvironmentApplicationService.addHardwarePlatformToGamingEnvironment(snes, snesHwpId).get();

        String snesHWM1Id = UUID.randomUUID().toString();
        String snesHWM2Id = UUID.randomUUID().toString();
        String snesHWM3Id = UUID.randomUUID().toString();
        String snesHWM4Id = UUID.randomUUID().toString();

        hardwareModelApplicationService.createNewHardwareModel(snesHWM1Id, "Japanese SHVC-001 model", HardwareModelType.HARDWARE_MODEL).get();
        hardwarePlatformApplicationService.addHardwareModelToHardwarePlatform(snesHwpId, snesHWM1Id).get();

        hardwareModelApplicationService.createNewHardwareModel(snesHWM2Id, "PAL-region SNSP-001A model", HardwareModelType.HARDWARE_MODEL).get();
        hardwarePlatformApplicationService.addHardwareModelToHardwarePlatform(snesHwpId, snesHWM2Id).get();

        hardwareModelApplicationService.createNewHardwareModel(snesHWM3Id, "Japanese SHVC-101 model", HardwareModelType.HARDWARE_MODEL).get();
        hardwarePlatformApplicationService.addHardwareModelToHardwarePlatform(snesHwpId, snesHWM3Id).get();

        hardwareModelApplicationService.createNewHardwareModel(snesHWM4Id, "SNES mini", HardwareModelType.NEW_HARDWARE_WITH_EMULATOR).get();
        hardwarePlatformApplicationService.addHardwareModelToHardwarePlatform(snesHwpId, snesHWM4Id).get();

        /*
        Japanese SHVC-001 model
        PAL-region SNSP-001A model
        Japanese SHVC-101 model
         */


        //#########  C64   #######################
        String c64 = UUID.randomUUID().toString();
        gamingEnvironmentApplicationService.createNewGamingEnvironment(c64, "Commodore 64")
                .get();
        System.out.println("step1");
        gamingEnvironmentApplicationService.addYearOfFirstRelease(c64, Year.of(1982)).get();

        String c64HwpId = UUID.randomUUID().toString();
        hardwarePlatformApplicationService.createNewHardwarePlatform(c64HwpId, "Commodore MOS machine & compatibles", HardwarePlatformType.HOME_COMPUTERS_EUROPE_NORTHAMERICA).get();
        gamingEnvironmentApplicationService.addHardwarePlatformToGamingEnvironment(c64, c64HwpId).get();

        String c64HHwmC64OriginalId = UUID.randomUUID().toString();
        String c64HHwmC64_I_Id = UUID.randomUUID().toString();
        String c64HHwmC64_II_Id = UUID.randomUUID().toString();
        String c64HHwmC64_Mini_Id = UUID.randomUUID().toString();

        hardwareModelApplicationService.createNewHardwareModel(c64HHwmC64OriginalId, "original C64", HardwareModelType.HARDWARE_MODEL).get();
        hardwarePlatformApplicationService.addHardwareModelToHardwarePlatform(c64HwpId, c64HHwmC64OriginalId).get();

        hardwareModelApplicationService.createNewHardwareModel(c64HHwmC64_I_Id, "C64-I", HardwareModelType.HARDWARE_MODEL).get();
        hardwarePlatformApplicationService.addHardwareModelToHardwarePlatform(c64HwpId, c64HHwmC64_I_Id).get();

        hardwareModelApplicationService.createNewHardwareModel(c64HHwmC64_II_Id, "C64-II", HardwareModelType.HARDWARE_MODEL).get();
        hardwarePlatformApplicationService.addHardwareModelToHardwarePlatform(c64HwpId, c64HHwmC64_II_Id).get();

        hardwareModelApplicationService.createNewHardwareModel(c64HHwmC64_Mini_Id, "The C64 Mini", HardwareModelType.NEW_HARDWARE_WITH_EMULATOR).get();
        hardwarePlatformApplicationService.addHardwareModelToHardwarePlatform(c64HwpId, c64HHwmC64_Mini_Id).get();


        //#########  AMIGA   #######################
        String amiga = UUID.randomUUID().toString();
        gamingEnvironmentApplicationService.createNewGamingEnvironment(amiga, "Commodore Amiga").get();
        gamingEnvironmentApplicationService.addYearOfFirstRelease(amiga, Year.of(1985)).get();

        String amigaHwpId = UUID.randomUUID().toString();
        hardwarePlatformApplicationService.createNewHardwarePlatform(amigaHwpId, "Amiga 68k machine", HardwarePlatformType.HOME_COMPUTERS_EUROPE_NORTHAMERICA).get();
        gamingEnvironmentApplicationService.addHardwarePlatformToGamingEnvironment(amiga, amigaHwpId).get();

        String amigaHwmAmiga500Id = UUID.randomUUID().toString();
        String amigaHwmAmiga1000Id = UUID.randomUUID().toString();
        String amigaHwmAmiga2000Id = UUID.randomUUID().toString();
        String amigaHwmAmiga1200Id = UUID.randomUUID().toString();
        String amigaHwmAmiga600Id = UUID.randomUUID().toString();
        String amigaHwmAmiga4000Id = UUID.randomUUID().toString();
        String amigaHwmAmiga3000Id = UUID.randomUUID().toString();

        hardwareModelApplicationService.createNewHardwareModel(amigaHwmAmiga500Id, "Amiga 500", HardwareModelType.HARDWARE_MODEL).get();
        hardwarePlatformApplicationService.addHardwareModelToHardwarePlatform(amigaHwpId, amigaHwmAmiga500Id);

        hardwareModelApplicationService.createNewHardwareModel(amigaHwmAmiga1000Id, "Amiga 1000", HardwareModelType.HARDWARE_MODEL).get();
        hardwarePlatformApplicationService.addHardwareModelToHardwarePlatform(amigaHwpId, amigaHwmAmiga1000Id);

        hardwareModelApplicationService.createNewHardwareModel(amigaHwmAmiga2000Id, "Amiga 2000", HardwareModelType.HARDWARE_MODEL).get();
        hardwarePlatformApplicationService.addHardwareModelToHardwarePlatform(amigaHwpId, amigaHwmAmiga2000Id);

        hardwareModelApplicationService.createNewHardwareModel(amigaHwmAmiga1200Id, "Amiga 1200", HardwareModelType.HARDWARE_MODEL).get();
        hardwarePlatformApplicationService.addHardwareModelToHardwarePlatform(amigaHwpId, amigaHwmAmiga1200Id);

        hardwareModelApplicationService.createNewHardwareModel(amigaHwmAmiga600Id, "Amiga 600", HardwareModelType.HARDWARE_MODEL).get();
        hardwarePlatformApplicationService.addHardwareModelToHardwarePlatform(amigaHwpId, amigaHwmAmiga600Id);

        hardwareModelApplicationService.createNewHardwareModel(amigaHwmAmiga4000Id, "Amiga 4000", HardwareModelType.HARDWARE_MODEL).get();
        hardwarePlatformApplicationService.addHardwareModelToHardwarePlatform(amigaHwpId, amigaHwmAmiga4000Id);

        hardwareModelApplicationService.createNewHardwareModel(amigaHwmAmiga3000Id, "Amiga 3000", HardwareModelType.HARDWARE_MODEL).get() ;
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