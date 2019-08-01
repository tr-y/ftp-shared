package com.edu.gcu.ftp.shared.demo.service;

import com.edu.gcu.ftp.shared.demo.entity.FileUser;
import com.edu.gcu.ftp.shared.demo.entity.FtpFile;
import java.util.List;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public interface FileUserService {

   int  getById(int parentId);
   int insertFileUser (FileUser fileUser);
//   List<FTPFile> getdirList (int id ,String path);
   void  delete(int id,int userId,int parentId);
   List<FileUser>findListById(int userId,int parent);
   void  updateByPrimaryKeyWithBLOBs(FileUser record);
   List<FileUser> searchByOrder(String order,int userId,int state);
//   boolean  insertFTPFile(List<FTPFile> ftpFiles);
   boolean findByName(String name,int userId ,int parentId);
}
