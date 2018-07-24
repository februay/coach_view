package indi.xp.coachview.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import indi.xp.coachview.model.SysUserRole;

@Mapper
public interface SysUserRoleMapper {

    public void add(@Param("entity") SysUserRole entity);

    public List<SysUserRole> findListByRoleId(@Param("roleId") String roleId);

    public List<SysUserRole> findListByUid(@Param("uid") String uid);

    public List<SysUserRole> findByWhere(@Param("paramMap") Map<String, Object[]> paramMap);

    public void updateByWhere(@Param("updateMap") Map<String, Object> updateMap,
        @Param("paramMap") Map<String, Object[]> paramMap);
}
