package indi.xp.coachview.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import indi.xp.coachview.dao.SysRoleDao;
import indi.xp.coachview.model.SysRole;
import indi.xp.coachview.service.SysRoleService;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleDao sysRoleDao;

    public SysRole getById(String id) {
        return sysRoleDao.getById(id);
    }

    public SysRole getByCode(String code) {
        return sysRoleDao.getByCode(code);
    }

    public List<SysRole> findListByIdList(List<String> idList) {
        return sysRoleDao.findListByIdList(idList);
    }
    
    @Override
    public List<SysRole> findListByCodeList(List<String> codeList) {
        return sysRoleDao.findListByCodeList(codeList);
    }

    public List<SysRole> findList() {
        return sysRoleDao.findList();
    }

}
