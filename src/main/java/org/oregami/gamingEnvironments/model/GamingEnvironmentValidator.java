package org.oregami.gamingEnvironments.model;

import org.oregami.common.CommonError;
import org.oregami.common.CommonErrorContext;
import org.oregami.common.CommonResult;
import org.oregami.gamingEnvironments.command.AddYearOfFirstReleaseCommand;
import org.oregami.gamingEnvironments.command.CreateGamingEnvironmentCommand;
import org.oregami.gamingEnvironments.command.CreateHardwareModelCommand;
import org.oregami.gamingEnvironments.command.CreateHardwarePlatformCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class GamingEnvironmentValidator {

    @Autowired
    GamingEnvironmentRepository gamingEnvironmentRepository;


    public CommonResult<Object> validate(CreateGamingEnvironmentCommand c) {
        return validateWorkingTitle(c.getWorkingTitle());
    }

    public CommonResult<Object> validate(CreateHardwarePlatformCommand c) {
        return validateWorkingTitle(c.getWorkingTitle());
    }

    public CommonResult<Object> validate(CreateHardwareModelCommand c) {
        return validateWorkingTitle(c.getWorkingTitle());
    }

    private CommonResult<Object> validateWorkingTitle(String workingTitle) {
        List<CommonError> errors = new ArrayList<>();

        if (StringUtils.isEmpty(workingTitle)) {
            CommonError empty = new CommonError(new CommonErrorContext("workingTitle"), "MSG_WORKING_TITLE_EMPTY");
            errors.add(empty);
        } else {
            if(workingTitle.length()<2) {
                CommonError empty = new CommonError(new CommonErrorContext("workingTitle"), "MSG_WORKING_TITLE_TOO_SHORT");
                errors.add(empty);
            }
        }

        CommonResult<Object> result = new CommonResult<Object>(errors);
        return result;
    }


    public CommonResult<Object> validate(AddYearOfFirstReleaseCommand c) {
        List<CommonError> errors = new ArrayList<>();
        if (c.getYearOfFirstRelease()==null) {
            CommonError error = new CommonError(new CommonErrorContext("yearOfFirstRelease"), "MSG_YEAR_EMPTY");
            errors.add(error);
        } else if (c.getYearOfFirstRelease().getValue() < 1900 || c.getYearOfFirstRelease().getValue() > 2100) {
            CommonError error = new CommonError(new CommonErrorContext("yearOfFirstRelease"), "MSG_YEAR_NOT_VALID");
            errors.add(error);
        }

        return new CommonResult<Object>(errors);
    }







    /*
    public CommonResult<Object> validate(AddTitleUsageCommand c) {
        List<CommonError> errors = new ArrayList<>();
        RGamingEnvironment gamingEnvironment = gamingEnvironmentRepository.findOne(c.getGamingEnvironmentId());

        //search for identical text
        if (errors.isEmpty()) {
            CommonError checkForDuplicates = checkForDuplicates(gamingEnvironment, c);
            if (checkForDuplicates!=null) {
                errors.add(checkForDuplicates);
            }
        }

        //check if region-subregion is ok
        CommonError checkSubRegions = checkSubRegions(gamingEnvironment, c);
        if (checkSubRegions!=null) {
            errors.add(checkSubRegions);
        }

        CommonResult<Object> result = new CommonResult<Object>(errors);
        return result;
    }

    protected CommonError checkSubRegions(RGamingEnvironment gamingEnvironment, AddTitleUsageCommand c) {
        for (RTitle t: gamingEnvironment.getGametitles()) {
            if (t.getId().equals(c.getTitleId())) {
                for (RTitleUsage tu: t.getTitleUsages()) {
                    if (Region.isSubRegionOf(tu.getRegion(), c.getRegion()) || Region.isSubRegionOf(c.getRegion(), tu.getRegion())) {
                        return (new CommonError(new CommonErrorContext("region", c.getTitleId()), REGIONS_NOT_CONSISTENT.name()));
                    }
                }
            }
        }
        return null;
    }

    protected CommonError checkForDuplicates(RGamingEnvironment gamingEnvironment, AddTitleUsageCommand c) {
        Set<RTitle> gametitles = gamingEnvironment.getGametitles();

        for(RTitle t: gametitles) {
            if (t.getId().equals(c.getTitleId())) {
                for (RTitleUsage tu : t.getTitleUsages()) {
                    if (tu.getRegion().equals(c.getRegion())) {
                        return (new CommonError(new CommonErrorContext("region", c.getTitleId()), DUPLICATE_TITLE_USAGE_FOR_REGION.name()));
                    }
                }
            }
        }
        return null;
    }
    */

}
