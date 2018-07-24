package indi.xp.coachview.service;

import java.util.List;

import indi.xp.coachview.model.SysRole;

public interface SysRoleService {

    public SysRole getById(String id);

    public SysRole getByCode(String code);

    public List<SysRole> findListByIdList(List<String> idList);

    public List<SysRole> findListByCodeList(List<String> codeList);

    public List<SysRole> findList();

}
