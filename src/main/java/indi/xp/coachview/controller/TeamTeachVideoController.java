package indi.xp.coachview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import indi.xp.coachview.common.Constants;
import indi.xp.coachview.model.TeamTeachVideo;
import indi.xp.coachview.service.TeamTeachVideoService;
import indi.xp.common.constants.MediaType;
import indi.xp.common.restful.ResponseResult;

@RestController("teamTeachVideoController")
@RequestMapping("/team-teach-video")
public class TeamTeachVideoController {

    @Autowired
    private TeamTeachVideoService teamTeachVideoService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<TeamTeachVideo> getById(@PathVariable(value = "id") String id,
        @RequestHeader(value = Constants.Header.TOKEN, required = true) String token,
        @RequestHeader(value = Constants.Header.TRACE_ID, required = false) String traceId) {
        return ResponseResult.buildResult(teamTeachVideoService.getById(id));
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<TeamTeachVideo> add(@RequestBody TeamTeachVideo teachVideo,
        @RequestHeader(value = Constants.Header.TOKEN, required = true) String token,
        @RequestHeader(value = Constants.Header.TRACE_ID, required = false) String traceId) {
        return ResponseResult.buildResult(teamTeachVideoService.add(teachVideo));
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<TeamTeachVideo> updateTeamMember(@RequestBody TeamTeachVideo teachVideo,
        @RequestHeader(value = Constants.Header.TOKEN, required = true) String token,
        @RequestHeader(value = Constants.Header.TRACE_ID, required = false) String traceId) {
        return ResponseResult.buildResult(teamTeachVideoService.update(teachVideo));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON)
    public void deleteTeamMember(@PathVariable(value = "id") String id,
        @RequestHeader(value = Constants.Header.TOKEN, required = true) String token,
        @RequestHeader(value = Constants.Header.TRACE_ID, required = false) String traceId) {

        teamTeachVideoService.delete(id);
    }

}
