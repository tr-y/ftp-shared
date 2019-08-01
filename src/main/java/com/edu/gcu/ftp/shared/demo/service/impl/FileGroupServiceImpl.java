package com.edu.gcu.ftp.shared.demo.service.impl;

import com.edu.gcu.ftp.shared.demo.dao.FileGroupMapper;
import com.edu.gcu.ftp.shared.demo.entity.FileGroup;
import com.edu.gcu.ftp.shared.demo.service.FileGroupService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileGroupServiceImpl implements FileGroupService {


  @Autowired
  FileGroupMapper groupMapper;


  @Override
  public List<FileGroup> findByGroupIdAndParent(int groupId ,int parent) {
    List  list = groupMapper.findByGroupIdAndParent(groupId,parent);
    return  list;
  }

  @Override
  public void insertFileUser(FileGroup fileUser) {
    groupMapper.insert(fileUser);
  }

  @Override
  public boolean updateFileGroup(FileGroup fileGroup) {
    if( groupMapper.updateByPrimaryKeySelective(fileGroup)>0){
      return true;
    }
    return false;
  }

  @Override
  public boolean deleteFileGroup(Integer id) {
    if(groupMapper.deleteByPrimaryKey(id)>0){
      return true;
    }
    return false;
  }

  @Override
  public List searchByOrder(int groupId, String order, int state) {
    List list = groupMapper.searchByOrder(groupId,order,state);
    if(list!=null){
      return list;
    }
    return null;
  }

  @Override
  public boolean findByName(String name, int groupId, int parentId) {
    if(groupMapper.findByName(name,groupId,parentId)!=null){
      return  true;
    }
    return false;
  }

  @Override
  public List findByFileIdAndUserId() {
    return null;
  }

  @Override
  public List findListByFileId(int fileId) {
    List list = groupMapper.findListByFileId(fileId);
    if(list.isEmpty()){
      return null;
    }
    return list;
  }
}
