package com.edu.gcu.ftp.shared.demo.service.impl;

import com.edu.gcu.ftp.shared.demo.dao.UserGroupMapper;
import com.edu.gcu.ftp.shared.demo.entity.UserGroup;
import com.edu.gcu.ftp.shared.demo.service.UserGroupService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserGroupServiceImpl implements UserGroupService {

  @Autowired
  UserGroupMapper userGroupMapper;

  @Override
  public void add(UserGroup userGroup) {
    userGroupMapper.insertSelective(userGroup);
  }

  @Override
  public List findList(int groupId,int send,int userId) {
   List list =  userGroupMapper.findList(groupId,send,userId);
   if(!list.isEmpty()){
     return  list ;
   }
    return null;
  }

  @Override
  public void delete(int id) {
    userGroupMapper.deleteByPrimaryKey(id);
  }

  @Override
  public void update(UserGroup userGroup) {
    userGroupMapper.updateByPrimaryKeySelective(userGroup);
  }
}
