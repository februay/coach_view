package indi.xp.coachview.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import indi.xp.coachview.dao.SysUserRoleDao;
import indi.xp.coachview.model.SysUserRole;
import indi.xp.coachview.service.SysUserRoleService;

@Service
public class SysUserRoleServiceImple implements SysUserRoleService {

    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    public List<SysUserRole> findListByRoleId(String roleId) {
        return sysUserRoleDao.findListByRoleId(roleId);
    }

    public List<SysUserRole> findListByUid(String uid) {
        return sysUserRoleDao.findListByUid(uid);
    }

    @Override
    public void add(SysUserRole entity) {
        sysUserRoleDao.add(entity);
    }

    @Override
    public void deleteUserRole(String uid, List<String> roleIdList) {
        sysUserRoleDao.deleteUserRole(uid, roleIdList);
    }

}
