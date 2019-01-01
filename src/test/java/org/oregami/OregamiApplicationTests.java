package org.oregami;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.oregami.gamingEnvironments.application.GamingEnvironmentApplicationService;
import org.oregami.gamingEnvironments.application.HardwarePlatformApplicationService;
import org.oregami.gamingEnvironments.model.GamingEnvironmentRepository;
import org.oregami.gamingEnvironments.model.HardwarePlatformRepository;
import org.oregami.gamingEnvironments.readmodel.withTitles.RGamingEnvironment;
import org.oregami.gamingEnvironments.readmodel.withTitles.RHardwarePlatform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Year;
import java.util.Optional;

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


	@Test
	public void gamingEnvironmentApplicationServiceTest() {

	    gamingEnvironmentApplicationService.createNewGamingEnvironment("idc64GE", "Commodore 64");

        Optional<RGamingEnvironment> c64GE = gamingEnvironmentRepository.findById("idc64GE");

        Assert.assertNotNull(c64GE);
        RGamingEnvironment rGamingEnvironment = c64GE.get();
        Assert.assertEquals("Commodore 64", rGamingEnvironment.getWorkingTitle());

        gamingEnvironmentApplicationService.addYearOfFirstRelease("idc64GE", Year.of(1982));


        hardwarePlatformApplicationService.createNewHardwarePlatform("idc64HWP", "Original C64");
        RHardwarePlatform c64HWP = hardwarePlatformRepository.findById("idc64HWP").get();
        Assert.assertNotNull(c64HWP);
        Assert.assertEquals("Original C64", c64HWP.getWorkingTitle());


        hardwarePlatformApplicationService.createNewHardwarePlatform("idc64miniHWP", "The C64 mini");
        RHardwarePlatform c64miniHWP = hardwarePlatformRepository.findById("idc64miniHWP").get();
        Assert.assertNotNull(c64miniHWP);
        Assert.assertEquals("The C64 mini", c64miniHWP.getWorkingTitle());

        hardwarePlatformApplicationService.createNewHardwarePlatform("idc128HWP", "Commodore 128");
        RHardwarePlatform c128HWP = hardwarePlatformRepository.findById("idc128HWP").get();
        Assert.assertNotNull(c128HWP);

        gamingEnvironmentApplicationService.addHardwarePlatformToGamingEnvironment("idc64GE", "idc64HWP");
        gamingEnvironmentApplicationService.addHardwarePlatformToGamingEnvironment("idc64GE", "idc64miniHWP");
        gamingEnvironmentApplicationService.addHardwarePlatformToGamingEnvironment("idc64GE", "idc128HWP");



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
