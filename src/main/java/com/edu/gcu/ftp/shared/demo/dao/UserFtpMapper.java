package com.edu.gcu.ftp.shared.demo.dao;

import com.edu.gcu.ftp.shared.demo.entity.UserFtp;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserFtpMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserFtp record);

    int insertSelective(UserFtp record);

    UserFtp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserFtp record);

    int updateByPrimaryKey(UserFtp record);


}