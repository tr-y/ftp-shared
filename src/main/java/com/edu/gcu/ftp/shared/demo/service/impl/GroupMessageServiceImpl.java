package com.edu.gcu.ftp.shared.demo.service.impl;

import com.edu.gcu.ftp.shared.demo.dao.GroupMessageMapper;
import com.edu.gcu.ftp.shared.demo.entity.GroupMessage;
import com.edu.gcu.ftp.shared.demo.service.GroupMessageService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupMessageServiceImpl implements GroupMessageService {

  @Autowired
  GroupMessageMapper groupMessageMapper;

  @Override
  public List findListByGroupId(int groupId) {
    return  groupMessageMapper.findListByGroupId(groupId);
  }

  @Override
  public void addMessage( GroupMessage groupMessage) {
      groupMessageMapper.insert(groupMessage);
  }

  @Override
  public void delete(int id) {
    groupMessageMapper.deleteByPrimaryKey(id);
  }


}
