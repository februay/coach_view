package indi.xp.coachview.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import indi.xp.coachview.system.VerificationCodeManager;
import indi.xp.common.constants.MediaType;

@RestController("publicController")
@RequestMapping("/public")
public class PublicController {

    @RequestMapping(value = "verification-code/{phoneNumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public void getVerificationCode(@PathVariable(value = "phoneNumber") String phoneNumber) {
        VerificationCodeManager.buildVerificationCode(phoneNumber);
    }

}
