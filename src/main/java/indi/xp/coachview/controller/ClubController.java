package indi.xp.coachview.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import indi.xp.coachview.model.Club;
import indi.xp.coachview.service.ClubService;
import indi.xp.common.constants.MediaType;
import indi.xp.common.restful.ResponseResult;

@RestController("clubController")
@RequestMapping("/club")
public class ClubController {

    @Autowired
    private ClubService clubService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<List<Club>> findUserList() {
        return ResponseResult.buildResult(clubService.findList());
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<Club> getByUid(@PathVariable(value = "id") String id) {
        return ResponseResult.buildResult(clubService.getById(id));
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<Club> add(@RequestBody Club club) {
        return ResponseResult.buildResult(clubService.add(club));
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON)
    public ResponseResult<Club> updateUser(@RequestBody Club club) {
        return ResponseResult.buildResult(clubService.update(club));
    }

}
