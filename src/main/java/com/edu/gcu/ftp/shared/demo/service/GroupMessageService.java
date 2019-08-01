package com.edu.gcu.ftp.shared.demo.service;

import com.edu.gcu.ftp.shared.demo.entity.GroupMessage;
import java.util.List;

public interface GroupMessageService {

  List findListByGroupId(int groupId);

  void addMessage(GroupMessage groupMessage);
  void delete(int id);
}
