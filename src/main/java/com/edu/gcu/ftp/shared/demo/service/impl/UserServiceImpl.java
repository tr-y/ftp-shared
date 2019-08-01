package com.edu.gcu.ftp.shared.demo.service.impl;

import com.edu.gcu.ftp.shared.demo.dao.UserMapper;
import com.edu.gcu.ftp.shared.demo.entity.User;
import com.edu.gcu.ftp.shared.demo.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  UserMapper userMapper;



  @Override
  public User selectUser(String userId,String password) {
    User user = userMapper.getUserById(userId);
    if(user != null){
     if(user.getPassword().trim().equals(password.trim())) {
       return user;
     }
    }
    return null; //没有找到
  }

  @Override
  public void  updateUserById(int userId ,int groupId){
    userMapper.updateGoupById(userId,groupId);
  }

  @Override
  public int isExist(String userId) {
    User user = userMapper.getUserById(userId);
    if(user != null){
      return 1;
    }
    return -1; //没有找到
  }

  @Override
  public void addUser(User user) {
     userMapper.insert(user);
  }

  @Override
  public List findListByGroupId(int groupId) {
    List list = userMapper.findListByGroupId(groupId);
    if(list.isEmpty()){
      return null;
    }
    return list;
  }

  @Override
  public void update(User user) {
    userMapper.updateByPrimaryKeySelective(user);
  }



}
