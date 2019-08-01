package com.edu.gcu.ftp.shared.demo.service.impl;

import com.edu.gcu.ftp.shared.demo.dao.FileSplitMapper;
import com.edu.gcu.ftp.shared.demo.entity.FileSplit;
import com.edu.gcu.ftp.shared.demo.service.FileSplitService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileSplitServiceImpl implements FileSplitService {


  @Autowired
  FileSplitMapper fileSplitMapper;

  @Override
  public List<FileSplit> findListByFileId(String fileId) {
     List list =  fileSplitMapper.findListByFileId(fileId);
     if(list.isEmpty()){
       return null;
     }
    return list;
  }

  @Override
  public void insert(FileSplit fileSplit) {
    fileSplitMapper.insert(fileSplit);
  }

  @Override
  public void delete(int id) {
    fileSplitMapper.deleteByPrimaryKey(id);
  }

  @Override
  public void update(FileSplit fileSplit) {
    fileSplitMapper.updateByPrimaryKeySelective(fileSplit);
  }
}
