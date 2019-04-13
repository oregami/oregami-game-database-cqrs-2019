package org.oregami;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.oregami.common.CommonError;
import org.oregami.common.CommonErrorContext;
import org.oregami.common.ValidationException;
import org.oregami.gamingEnvironments.application.GamingEnvironmentApplicationService;
import org.oregami.gamingEnvironments.application.HardwareModelApplicationService;
import org.oregami.gamingEnvironments.application.HardwarePlatformApplicationService;
import org.oregami.gamingEnvironments.model.GamingEnvironmentRepository;
import org.oregami.gamingEnvironments.model.HardwareModelRepository;
import org.oregami.gamingEnvironments.model.HardwarePlatformRepository;
import org.oregami.gamingEnvironments.model.types.HardwareModelType;
import org.oregami.gamingEnvironments.model.types.HardwarePlatformType;
import org.oregami.gamingEnvironments.readmodel.withTitles.RGamingEnvironment;
import org.oregami.gamingEnvironments.readmodel.withTitles.RHardwarePlatform;
import org.oregami.references.application.ReferenceApplicationService;
import org.oregami.references.model.ReferenceRepository;
import org.oregami.references.model.types.ReferenceType;
import org.oregami.references.readmodel.RReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Year;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OregamiApplicationTests {


    @Autowired
    private GamingEnvironmentApplicationService gamingEnvironmentApplicationService;

    @Autowired
    private GamingEnvironmentRepository gamingEnvironmentRepository;

    @Autowired
    private HardwarePlatformApplicationService hardwarePlatformApplicationService;

    @Autowired
    private HardwarePlatformRepository hardwarePlatformRepository;

    @Autowired
    HardwareModelApplicationService hardwareModelApplicationService;

    @Autowired
    HardwareModelRepository hardwareModelRepository;

    @Autowired
    ReferenceApplicationService referenceApplicationService;

    @Autowired
    ReferenceRepository referenceRepository;


    @Test
    public void validationTest() {

        CompletableFuture<Object> completableFuture = gamingEnvironmentApplicationService.createNewGamingEnvironment("id", "");

        try {
            Object result = completableFuture.get();

        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {

            if (e.getCause() instanceof ValidationException) {
                System.out.println("Validation error: " + e.getCause().toString());

            } else {
                List<CommonError> errors = new ArrayList<>();
                errors.add(new CommonError(new CommonErrorContext("general"), e.getMessage()));
                System.out.println("Other error: " + e.getCause().toString());
            }
        }



    }

    @Test
    public void ref1() {
        HashSet<String> eventIDs = new HashSet<>();
        eventIDs.add("eventId1");
        referenceApplicationService.createNewReference("id1", ReferenceType.FAN_SITE, eventIDs, null, null);
    }

    @Test
    public void ref2() {
        HashSet<String> eventIDs = new HashSet<>();
        referenceApplicationService.createNewReference("id1", ReferenceType.FAN_SITE, eventIDs, null, null);
        eventIDs.add("eventId1");
        referenceApplicationService.addEventId("id1", "eventId2");
    }

    @Test
    public void referenceTest() {
        String id = "id4242";
        Set<String> eventIdSet = new HashSet<>();
        eventIdSet.add("test_event_id1");

        referenceApplicationService.createNewReference(id, ReferenceType.OFFICIAL_SITE, eventIdSet, null, null);
        referenceApplicationService.addUrl(id, "http://www.kultpower.de");
        referenceApplicationService.addEventId(id, "test_event_id2");

        referenceApplicationService.addDescription(id,"desc text");

        RReference refLoaded = referenceRepository.findById(id).get();

        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(refLoaded));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        List<RReference> resultSizeOne = referenceRepository.findByEventIdList("test_event_id1");
        Assert.assertThat(resultSizeOne.size(), Matchers.is(1));
        Assert.assertThat(resultSizeOne.get(0).getDescription(), Matchers.is("desc text"));

        List<RReference> resultEmtpy = referenceRepository.findByEventIdList("non_existent_event_id");
        Assert.assertThat(resultEmtpy.size(), Matchers.is(0));


    }

	@Test
	public void gamingEnvironmentApplicationServiceTest() {

	    gamingEnvironmentApplicationService.createNewGamingEnvironment("idc64GE", "Commodore 64");

        Optional<RGamingEnvironment> c64GE = gamingEnvironmentRepository.findById("idc64GE");

        Assert.assertNotNull(c64GE);
        RGamingEnvironment rGamingEnvironment = c64GE.get();
        Assert.assertEquals("Commodore 64", rGamingEnvironment.getWorkingTitle());

        gamingEnvironmentApplicationService.addYearOfFirstRelease("idc64GE", Year.of(1982));


        hardwarePlatformApplicationService.createNewHardwarePlatform("idc64HWP", "Commodore MOS machine & compatibles", HardwarePlatformType.HOME_COMPUTERS_EUROPE_NORTHAMERICA);
        RHardwarePlatform c64HWP = hardwarePlatformRepository.findById("idc64HWP").get();
        Assert.assertNotNull(c64HWP);
        Assert.assertEquals("Commodore MOS machine & compatibles", c64HWP.getWorkingTitle());

        gamingEnvironmentApplicationService.addHardwarePlatformToGamingEnvironment("idc64GE", "idc64HWP");


        hardwareModelApplicationService.createNewHardwareModel("idHmC64original", "original C64", HardwareModelType.HARDWARE_MODEL);
        hardwareModelApplicationService.createNewHardwareModel("idHmC64-I", "C64-I", HardwareModelType.HARDWARE_MODEL);
        hardwareModelApplicationService.createNewHardwareModel("idHmC64-II", "C64-II", HardwareModelType.HARDWARE_MODEL);
        hardwareModelApplicationService.createNewHardwareModel("idHmC64-Mini", "The C64 Mini", HardwareModelType.NEW_HARDWARE_WITH_EMULATOR);

        hardwarePlatformApplicationService.addHardwareModelToHardwarePlatform("idc64HWP", "idHmC64original");
        hardwarePlatformApplicationService.addHardwareModelToHardwarePlatform("idc64HWP", "idHmC64-I");
        hardwarePlatformApplicationService.addHardwareModelToHardwarePlatform("idc64HWP", "idHmC64-II");
        hardwarePlatformApplicationService.addHardwareModelToHardwarePlatform("idc64HWP", "idHmC64-Mini");

        Optional<RGamingEnvironment> ge = gamingEnvironmentRepository.findById("idc64GE");
        //System.out.println(ToStringBuilder.reflectionToString(ge.get(), ToStringStyle.MULTI_LINE_STYLE));

        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ge.get()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }



    }

}
