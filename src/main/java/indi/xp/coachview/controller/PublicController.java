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
import indi.xp.common.utils.sms.AliyunDysmsUtils;

@RestController("publicController")
@RequestMapping("/public")
public class PublicController {

    private static final boolean sendSmsOn = true;

    private static final Map<String, String> verificationCodeMap = new HashMap<String, String>();

    public static boolean validateVerificationCode(String key, String value) {
        return StringUtils.isNotBlank(key, value) && value.equals(verificationCodeMap.get(key));
    }

    @RequestMapping(value = "verification-code/{phoneNumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<String> getVerificationCode(@PathVariable(value = "phoneNumber") String phoneNumber) {
        String verificationCode = UuidUtils.generateRandomNumber(4);
        verificationCodeMap.put(phoneNumber, verificationCode);
        this.sendLoginVerificationCode(phoneNumber, verificationCode);
        return ResponseResult.buildResult(verificationCode);
    }

    private void sendLoginVerificationCode(String phoneNumber, String verificationCode) {
        if (sendSmsOn) {
            String signName = "Coachview";
            String templateCode = "SMS_140690149"; // 登录验证码模板
            Map<String, String> templateParamMap = new HashMap<String, String>();
            templateParamMap.put("code", verificationCode);
            AliyunDysmsUtils.sendSms(phoneNumber, signName, templateCode, templateParamMap);
        }
    }

}
