package indi.xp.coachview.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import indi.xp.common.constants.MediaType;
import indi.xp.common.restful.ResponseResult;
import indi.xp.common.utils.StringUtils;
import indi.xp.common.utils.UuidUtils;

@RestController("publicController")
@RequestMapping("/public")
public class PublicController {

    private static final Map<String, String> verificationCodeMap = new HashMap<String, String>();

    public static boolean validateVerificationCode(String key, String value) {
        return StringUtils.isNotBlank(key, value) && value.equals(verificationCodeMap.get(key));
    }

    @RequestMapping(value = "verification-code/{key}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<String> getVerificationCode(@PathVariable(value = "key") String key) {
        String verificationCode = UuidUtils.generateRandomNumber(4);
        verificationCodeMap.put(key, verificationCode);
        return ResponseResult.buildResult(verificationCode);
    }

}
