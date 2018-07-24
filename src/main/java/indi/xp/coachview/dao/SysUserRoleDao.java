package indi.xp.coachview.dao;

import java.util.List;

import indi.xp.coachview.model.SysUserRole;

public interface SysUserRoleDao {

    public List<SysUserRole> findListByRoleId(String roleId);

    public List<SysUserRole> findListByUid(String uid);

    public void add(SysUserRole entity);

    public void deleteUserRole(String uid, List<String> roleIdList);

}
