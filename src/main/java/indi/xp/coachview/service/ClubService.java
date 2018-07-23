package indi.xp.coachview.service;

import java.util.List;

import indi.xp.coachview.model.Club;

public interface ClubService {

    public Club getById(String id);

    public List<Club> findByIdList(List<String> idList);

    public List<Club> findList();

    public Club add(Club club);

    public Club update(Club club);

    public void delete(String id);

}
