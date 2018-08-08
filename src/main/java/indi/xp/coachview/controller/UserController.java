package indi.xp.coachview.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import indi.xp.coachview.common.BusinessErrorCodeEnum;
import indi.xp.coachview.common.Constants;
import indi.xp.coachview.model.SysRole;
import indi.xp.coachview.model.vo.ListItemVo;
import indi.xp.coachview.model.vo.UserSignInVo;
import indi.xp.coachview.model.vo.UserVo;
import indi.xp.coachview.service.SysRoleService;
import indi.xp.coachview.service.UserService;
import indi.xp.coachview.session.Session;
import indi.xp.coachview.session.SessionManager;
import indi.xp.common.constants.MediaType;
import indi.xp.common.exception.BusinessException;
import indi.xp.common.restful.ResponseResult;

@RestController("userController")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SessionManager sessionManager;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<List<UserVo>> findUserList(
        @RequestParam(value = "clubId", required = false) String clubId,
        @RequestParam(value = "schoolId", required = false) String schoolId,
        @RequestParam(value = "teamId", required = false) String teamId,
        @RequestHeader(value = Constants.Header.TOKEN, required = true) String token,
        @RequestHeader(value = Constants.Header.TRACE_ID, required = false) String traceId) {
        return ResponseResult.buildResult(userService.findUserList(clubId, schoolId, teamId));
    }
    
    @RequestMapping(value = "list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<List<ListItemVo>> findUserItemList(
        @RequestParam(value = "clubId", required = false) String clubId,
        @RequestParam(value = "schoolId", required = false) String schoolId,
        @RequestParam(value = "teamId", required = false) String teamId,
        @RequestHeader(value = Constants.Header.TOKEN, required = true) String token,
        @RequestHeader(value = Constants.Header.TRACE_ID, required = false) String traceId) {
        return ResponseResult.buildResult(userService.findUserItemList(clubId, schoolId, teamId));
    }

    @RequestMapping(value = "{uid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<UserVo> getUserByUid(@PathVariable(value = "uid") String uid,
        @RequestHeader(value = Constants.Header.TOKEN, required = true) String token,
        @RequestHeader(value = Constants.Header.TRACE_ID, required = false) String traceId) {
        return ResponseResult.buildResult(userService.getUserByUid(uid));
    }

    @RequestMapping(value = "{uid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserByUid(@PathVariable(value = "uid") String uid,
        @RequestHeader(value = Constants.Header.TOKEN, required = true) String token,
        @RequestHeader(value = Constants.Header.TRACE_ID, required = false) String traceId) {
        userService.deleteByUid(uid);
    }

    @RequestMapping(value = "{phone}/by-phone", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<UserVo> getUserByPhone(@PathVariable(value = "phone") String phone,
        @RequestHeader(value = Constants.Header.TOKEN, required = true) String token,
        @RequestHeader(value = Constants.Header.TRACE_ID, required = false) String traceId) {
        return ResponseResult.buildResult(userService.getUserByPhone(phone));
    }
    
    @RequestMapping(value = "check/{phone}/by-phone", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<Boolean> checkUserByPhone(@PathVariable(value = "phone") String phone,
        @RequestHeader(value = Constants.Header.TRACE_ID, required = false) String traceId) {
        boolean exist = userService.checkUserPhoneExists(phone, null);
        return ResponseResult.buildResult(exist);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<UserVo> addUser(@RequestBody UserVo userVo,
        @RequestHeader(value = Constants.Header.TOKEN, required = true) String token,
        @RequestHeader(value = Constants.Header.TRACE_ID, required = false) String traceId) {
        return ResponseResult.buildResult(userService.addUser(userVo));
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<UserVo> updateUser(@RequestBody UserVo userVo,
        @RequestHeader(value = Constants.Header.TOKEN, required = true) String token,
        @RequestHeader(value = Constants.Header.TRACE_ID, required = false) String traceId) {
        return ResponseResult.buildResult(userService.updateUser(userVo));
    }

    @RequestMapping(value = "sign-in", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<Session> signIn(@RequestBody UserSignInVo userSignInVo) {
        boolean passed = false;
        UserVo user = null;
        Session session = null;
        if (userSignInVo != null && UserSignInVo.TYPE_PHONE.equals(userSignInVo.getType())) {
            String phone = userSignInVo.getKey();
            passed = PublicController.validateVerificationCode(phone, userSignInVo.getValue());
            user = userService.getUserByPhone(phone);
        }
        if (!passed) {
            throw new BusinessException(BusinessErrorCodeEnum.USER_VERIFICATION_CODE_ERROR);
        } else if (user == null) {
            throw new BusinessException(BusinessErrorCodeEnum.USER_NOT_EXISTS);
        } else {
            // 登录成功返回session
            session = sessionManager.addSession(user);
        }
        return ResponseResult.buildResult(session);
    }

    @RequestMapping(value = "sign-out", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void signOut(@RequestHeader(value = Constants.Header.TOKEN, required = true) String token,
        @RequestHeader(value = Constants.Header.TRACE_ID, required = false) String traceId) {

        sessionManager.clearSession(token);
    }
    
    @RequestMapping(value = "roles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<List<SysRole>> findSysRoleList(
        @RequestHeader(value = Constants.Header.TOKEN, required = true) String token,
        @RequestHeader(value = Constants.Header.TRACE_ID, required = false) String traceId) {
        return ResponseResult.buildResult(sysRoleService.findList());
    }

}
