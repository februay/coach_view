package indi.xp.coachview.dao;

import java.util.List;

import indi.xp.coachview.model.SysRole;

public interface SysRoleDao {

    public SysRole getById(String id);

    public SysRole getByCode(String code);

    public List<SysRole> findListByIdList(List<String> idList);
    
    public List<SysRole> findListByCodeList(List<String> codeList);

    public List<SysRole> findList();

}
