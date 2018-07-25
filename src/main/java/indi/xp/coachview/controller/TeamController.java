package indi.xp.coachview.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import indi.xp.coachview.common.Constants;
import indi.xp.coachview.model.Team;
import indi.xp.coachview.model.TeamMember;
import indi.xp.coachview.service.TeamMemberService;
import indi.xp.coachview.service.TeamService;
import indi.xp.common.constants.MediaType;
import indi.xp.common.restful.ResponseResult;

@RestController("teamController")
@RequestMapping("/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamMemberService teamMemberService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<List<Team>> findTeamList(
        @RequestHeader(value = Constants.Header.TOKEN, required = true) String token,
        @RequestHeader(value = Constants.Header.TRACE_ID, required = false) String traceId) {
        return ResponseResult.buildResult(teamService.findList());
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<Team> getTeamById(@PathVariable(value = "id") String id,
        @RequestHeader(value = Constants.Header.TOKEN, required = true) String token,
        @RequestHeader(value = Constants.Header.TRACE_ID, required = false) String traceId) {
        return ResponseResult.buildResult(teamService.getById(id));
    }

    @RequestMapping(value = "{id}/team-member", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<List<TeamMember>> findTeamMemberListByTeamId(@PathVariable(value = "id") String id,
        @RequestHeader(value = Constants.Header.TOKEN, required = true) String token,
        @RequestHeader(value = Constants.Header.TRACE_ID, required = false) String traceId) {
        return ResponseResult.buildResult(teamMemberService.findTeamMemberListByTeamId(id));
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<Team> addTeam(@RequestBody Team team,
        @RequestHeader(value = Constants.Header.TOKEN, required = true) String token,
        @RequestHeader(value = Constants.Header.TRACE_ID, required = false) String traceId) {
        return ResponseResult.buildResult(teamService.add(team));
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<Team> updateTeam(@RequestBody Team team,
        @RequestHeader(value = Constants.Header.TOKEN, required = true) String token,
        @RequestHeader(value = Constants.Header.TRACE_ID, required = false) String traceId) {
        return ResponseResult.buildResult(teamService.update(team));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON)
    public void deleteTeam(@PathVariable(value = "id") String id,
        @RequestHeader(value = Constants.Header.TOKEN, required = true) String token,
        @RequestHeader(value = Constants.Header.TRACE_ID, required = false) String traceId) {

        teamService.delete(id);
    }

}
