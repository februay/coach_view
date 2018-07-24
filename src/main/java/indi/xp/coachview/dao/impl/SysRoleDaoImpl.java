package indi.xp.coachview.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import indi.xp.coachview.dao.SysRoleDao;
import indi.xp.coachview.mapper.SysRoleMapper;
import indi.xp.coachview.model.SysRole;

@Repository
public class SysRoleDaoImpl implements SysRoleDao {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    public SysRole getById(String id) {
        return sysRoleMapper.getById(id);
    }

    public SysRole getByCode(String code) {
        return sysRoleMapper.getByCode(code);
    }

    public List<SysRole> findListByIdList(List<String> idList) {
        return sysRoleMapper.findListByIdList(idList);
    }

    public List<SysRole> findListByCodeList(List<String> codeList) {
        return sysRoleMapper.findListByCodeList(codeList);
    }

    public List<SysRole> findList() {
        return sysRoleMapper.findList();
    }

}
