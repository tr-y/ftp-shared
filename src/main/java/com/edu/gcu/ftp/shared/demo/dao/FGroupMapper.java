package com.edu.gcu.ftp.shared.demo.dao;

import com.edu.gcu.ftp.shared.demo.entity.FGroup;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
@Mapper
@Component
public interface FGroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FGroup record);

    int insertSelective(FGroup record);

    FGroup selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FGroup record);

    int updateByPrimaryKey(FGroup record);

    List searchByName(@Param(value = "name") String name);

    List findList();
}