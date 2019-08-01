package com.edu.gcu.ftp.shared.demo.dao;

import com.edu.gcu.ftp.shared.demo.entity.User;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User getUserById(@Param(value = "userId")String userId);

    int updateGoupById(@Param(value = "userId") int userId ,@Param(value = "groupId")int groupId);

    List findListByGroupId(@Param(value = "groupId")int groupId );


}