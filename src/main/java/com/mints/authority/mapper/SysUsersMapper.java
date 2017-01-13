package com.mints.authority.mapper;

import com.mints.authority.entity.SysUsers;
import com.mints.base.BaseExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysUsersMapper {
    long countByExample(BaseExample example);

    int deleteByExample(BaseExample example);

    int deleteByPrimaryKey(String id);

    int insert(SysUsers record);

    int insertSelective(SysUsers record);

    List<SysUsers> selectByExample(BaseExample example);

    SysUsers selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SysUsers record, @Param("example") BaseExample example);

    int updateByExample(@Param("record") SysUsers record, @Param("example") BaseExample example);

    int updateByPrimaryKeySelective(SysUsers record);

    int updateByPrimaryKey(SysUsers record);
}