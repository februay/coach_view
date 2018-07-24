package indi.xp.coachview.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import indi.xp.coachview.model.SysRole;

@Mapper
public interface SysRoleMapper {

    public SysRole getById(@Param("id") String id);

    public SysRole getByCode(@Param("code") String code);

    public List<SysRole> findListByIdList(@Param("idList") List<String> idList);

    public List<SysRole> findListByCodeList(@Param("codeList") List<String> codeList);

    public List<SysRole> findList();

}
