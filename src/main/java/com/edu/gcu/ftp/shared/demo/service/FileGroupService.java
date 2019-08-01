package com.edu.gcu.ftp.shared.demo.service;

import com.edu.gcu.ftp.shared.demo.entity.FileGroup;
import java.util.List;

public interface FileGroupService {
  List<FileGroup> findByGroupIdAndParent(int groupId ,int parent);
  void  insertFileUser(FileGroup fileUser);
  boolean updateFileGroup(FileGroup fileGroup);
  boolean deleteFileGroup(Integer id);
  List searchByOrder(int groupId,String order,int state);
  boolean findByName(String name,int groupId,int parentId);
  List findByFileIdAndUserId();
  List findListByFileId(int fileId);
}
