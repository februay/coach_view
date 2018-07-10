package indi.xp.coachview.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import indi.xp.common.utils.DateUtils;

@RestController("helloController")
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String hello() {
        return "Hello World!";
    }

    @RequestMapping(value = "now", method = RequestMethod.GET)
    public String now() {
        return "现在时间：" + DateUtils.getDateTime();
    }

}
