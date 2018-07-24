package indi.xp.coachview.service;

import java.util.List;

import indi.xp.coachview.model.SysUserRole;

public interface SysUserRoleService {
    
    public void add(SysUserRole entity);

    public List<SysUserRole> findListByRoleId(String roleId);

    public List<SysUserRole> findListByUid(String uid);

    public void deleteUserRole(String uid, List<String> roleIdList);

}
