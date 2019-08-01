package com.edu.gcu.ftp.shared.demo.service;

import com.edu.gcu.ftp.shared.demo.entity.UserGroup;
import java.util.List;

public interface UserGroupService {


  void add(UserGroup userGroup);

  List findList(int groupId,int send,int userId);

  void delete(int id);

  void update(UserGroup userGroup);
}
