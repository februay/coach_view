package indi.xp.coachview.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import indi.xp.coachview.model.User;
import indi.xp.coachview.model.vo.UserSignInVo;
import indi.xp.coachview.model.vo.UserSignUpVo;
import indi.xp.coachview.service.UserService;
import indi.xp.common.constants.MediaType;
import indi.xp.common.restful.ResponseResult;

@RestController("userController")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<List<User>> findUserList() {
        return ResponseResult.buildResult(userService.findUserList());
    }

    @RequestMapping(value = "{uid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<User> getUserByUid(@PathVariable(value = "uid") String uid) {
        return ResponseResult.buildResult(userService.getUserByUid(uid));
    }

    @RequestMapping(value = "{phone}/by-phone", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<User> getUserByPhone(@PathVariable(value = "phone") String phone) {
        return ResponseResult.buildResult(userService.getUserByPhone(phone));
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<User> addUser(@RequestBody UserSignUpVo userSignUpVo) {
        return ResponseResult.buildResult(userService.addUser(userSignUpVo));
    }
    
    @RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<User> updateUser(@RequestBody UserSignUpVo userSignUpVo) {
        return ResponseResult.buildResult(userService.updateUser(userSignUpVo));
    }

    @RequestMapping(value = "sign-in", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<Boolean> signIn(@RequestBody UserSignInVo userSignInVo) {
        boolean pass = false;
        if (userSignInVo != null && UserSignInVo.TYPE_PHONE.equals(userSignInVo.getType())) {
            pass = PublicController.validateVerificationCode(userSignInVo.getKey(), userSignInVo.getValue());
        }
        return ResponseResult.buildResult(pass);
    }

}
