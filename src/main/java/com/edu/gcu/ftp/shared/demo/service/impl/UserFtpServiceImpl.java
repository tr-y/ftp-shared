package com.edu.gcu.ftp.shared.demo.service.impl;

import com.edu.gcu.ftp.shared.demo.dao.UserFtpMapper;
import com.edu.gcu.ftp.shared.demo.entity.UserFtp;
import com.edu.gcu.ftp.shared.demo.service.UserFtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserFtpServiceImpl  implements  UserFtpService{


  @Autowired
  UserFtpMapper userFtpMapper;

  public UserFtp getFtpUser(Integer id ) {
    return  userFtpMapper.selectByPrimaryKey(id);
  }

  @Override
  public boolean insert(UserFtp userFtp) {

    if(userFtpMapper.insert(userFtp)>0){
      return true;
    }

    return false;
  }

}
