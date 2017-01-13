package com.mints.authority.mapper;

import com.mints.authority.entity.SysRoles;
import com.mints.base.BaseExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysRolesMapper {
    long countByExample(BaseExample example);

    int deleteByExample(BaseExample example);

    int deleteByPrimaryKey(String id);

    int insert(SysRoles record);

    int insertSelective(SysRoles record);

    List<SysRoles> selectByExample(BaseExample example);

    SysRoles selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SysRoles record, @Param("example") BaseExample example);

    int updateByExample(@Param("record") SysRoles record, @Param("example") BaseExample example);

    int updateByPrimaryKeySelective(SysRoles record);

    int updateByPrimaryKey(SysRoles record);
}