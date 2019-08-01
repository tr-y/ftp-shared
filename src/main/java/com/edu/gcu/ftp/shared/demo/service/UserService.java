package com.edu.gcu.ftp.shared.demo.service;

import com.edu.gcu.ftp.shared.demo.entity.User;
import java.util.List;

public interface UserService {

  User selectUser (String userId,String password);

  void  updateUserById(int userId ,int groupId);

  int isExist(String userId);

  void addUser(User user);

  List findListByGroupId(int groupId);
  void update(User user);


}
