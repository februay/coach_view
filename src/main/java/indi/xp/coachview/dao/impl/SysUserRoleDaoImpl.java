package indi.xp.coachview.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import indi.xp.coachview.dao.SysUserRoleDao;
import indi.xp.coachview.mapper.SysUserRoleMapper;
import indi.xp.coachview.model.SysUserRole;
import indi.xp.common.utils.CollectionUtils;

@Repository
public class SysUserRoleDaoImpl implements SysUserRoleDao {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    public List<SysUserRole> findListByRoleId(String roleId) {
        return sysUserRoleMapper.findListByRoleId(roleId);
    }

    public List<SysUserRole> findListByUid(String uid) {
        return sysUserRoleMapper.findListByUid(uid);
    }

    @Override
    public void add(SysUserRole entity) {
        sysUserRoleMapper.add(entity);
    }

    @Override
    public void deleteUserRole(String uid, List<String> roleIdList) {

        Map<String, Object> updateMap = new HashMap<String, Object>();
        updateMap.put("delete_status", true);

        Map<String, Object[]> paramMap = new HashMap<String, Object[]>();
        paramMap.put("uid", new String[] { uid });
        if (CollectionUtils.isNotEmpty(roleIdList)) {
            paramMap.put("role_id", roleIdList.toArray());
        }

        sysUserRoleMapper.updateByWhere(updateMap, paramMap);
    }

}
