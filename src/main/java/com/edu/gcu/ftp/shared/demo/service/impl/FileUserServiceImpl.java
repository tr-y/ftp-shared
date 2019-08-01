package com.edu.gcu.ftp.shared.demo.service.impl;

import com.edu.gcu.ftp.shared.demo.dao.FileUserMapper;
import com.edu.gcu.ftp.shared.demo.entity.FileUser;
import com.edu.gcu.ftp.shared.demo.entity.FtpFile;
import com.edu.gcu.ftp.shared.demo.service.FileUserService;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class FileUserServiceImpl implements FileUserService {

  private static  String  LOCAL_CHARSET = "GBK";
  private static  String  SERVER_CHARSET = "ISO-8859-1";

  @Autowired
  FileUserMapper fileUserMapper;


  @Override
  public int getById(int parentId) {
    FileUser fileUser =  fileUserMapper.selectByPrimaryKey(parentId);
    if(fileUser!=null){
      return fileUser.getParent();
    }
    return 0;
  }

  @Override
  public int  insertFileUser(FileUser fileUser) {
       return  fileUserMapper.insert(fileUser);
  }


  public List<FTPFile>getdirList(FTPClient ftpclient,String path){
    List<FTPFile> files = null;
    try {
      files = Arrays.asList(ftpclient
          .listFiles(new String(path.getBytes(LOCAL_CHARSET), SERVER_CHARSET)));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return files;
  }




//  @Override
//  public List<FTPFile> getdirList (int id ,String path) {
//    List<FTPFile> files = null;
//    files = fileUserMapper.searchByUserpath(id,path);
//    return files;
//  }

  @Override
  public void  delete(int id,int userId,int parentId) {
    fileUserMapper.deleteByPrimaryKey(id);
  }



  @Override
  public List<FileUser> findListById(int userId,int parent) {
      return fileUserMapper.findListById(userId,parent);
  }

  @Override
  public void  updateByPrimaryKeyWithBLOBs(FileUser record) {
     fileUserMapper.updateByPrimaryKeyWithBLOBs(record);
  }

  @Override
  public List<FileUser> searchByOrder(String order,int userId,int state) {
    List list = fileUserMapper.searchByOrder(order,userId,state);
    if(list!=null){
      return list;
    }
    return null;
  }

  @Override
  public boolean findByName(String name, int userId, int parentId) {
    if(fileUserMapper.findByName(name,userId,parentId)!=null){
      return true;
    }
    return false;
  }

//  @Override
//  public boolean insertFTPFile(List<FTPFile> ftpFiles) {
//    if(fileUserMapper.insertFTPFile(ftpFiles)>0){
//      return true;
//    }
//    return false;
//  }


}
