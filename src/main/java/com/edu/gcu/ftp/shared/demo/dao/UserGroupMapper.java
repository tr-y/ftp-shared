package com.edu.gcu.ftp.shared.demo.dao;

import com.edu.gcu.ftp.shared.demo.entity.UserGroup;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserGroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserGroup record);

    int insertSelective(UserGroup record);

    UserGroup selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserGroup record);

    int updateByPrimaryKey(UserGroup record);

    List findList(@Param(value = "groupId") int groupId,@Param(value = "send") int send,@Param(value = "userId")int userId);

}