package com.edu.gcu.ftp.shared.demo.service.impl;

import com.edu.gcu.ftp.shared.demo.dao.FGroupMapper;
import com.edu.gcu.ftp.shared.demo.entity.FGroup;
import com.edu.gcu.ftp.shared.demo.service.FGroupService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FGroupServiceImpl implements FGroupService {

    @Autowired
    FGroupMapper groupMapper;

    @Override
    public int addFGroup(FGroup fGroup) {
        groupMapper.insert(fGroup);
        return fGroup.getId();
    }

    @Override
    public List searchByName(String name) {
       List list = groupMapper.searchByName(name);
       if(list.size()>0){
           return list;
       }
        return null;
    }

  @Override
  public List findList() {
      List list = groupMapper.findList();
      if(list.size()>0){
        return list;
      }
      return null;
  }
}