package com.edu.gcu.ftp.shared.demo.service;

import com.edu.gcu.ftp.shared.demo.entity.UserFtp;

public interface UserFtpService {

  UserFtp getFtpUser(Integer id );

  boolean insert(UserFtp userFtp);


}
