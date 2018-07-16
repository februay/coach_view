package indi.xp.coachview.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import indi.xp.coachview.model.TeamMember;
import indi.xp.coachview.service.TeamMemberService;
import indi.xp.common.constants.MediaType;
import indi.xp.common.restful.ResponseResult;

@RestController("teamMemberController")
@RequestMapping("/team-member")
public class TeamMemberController {

    @Autowired
    private TeamMemberService teamMemberService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<List<TeamMember>> findUserList() {
        return ResponseResult.buildResult(teamMemberService.findList());
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<TeamMember> getByUid(@PathVariable(value = "id") String id) {
        return ResponseResult.buildResult(teamMemberService.getById(id));
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<TeamMember> add(@RequestBody TeamMember teamMember) {
        return ResponseResult.buildResult(teamMemberService.add(teamMember));
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<TeamMember> updateUser(@RequestBody TeamMember teamMember) {
        return ResponseResult.buildResult(teamMemberService.update(teamMember));
    }

}
