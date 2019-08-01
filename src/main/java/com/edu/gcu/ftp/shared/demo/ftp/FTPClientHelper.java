package com.edu.gcu.ftp.shared.demo.ftp;

import com.edu.gcu.ftp.shared.demo.config.FtpPoolConfig;
import com.edu.gcu.ftp.shared.demo.util.FTPUtil;
import com.edu.gcu.ftp.shared.demo.util.FilePathUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

@Service
public class  FTPClientHelper {


  private FtpClientPool ftpClientPool;
  private static  String  LOCAL_CHARSET = "GBK";
  private static  String  SERVER_CHARSET = "ISO-8859-1";
  private static Logger log = LoggerFactory.getLogger(FTPClientHelper.class);
  public FilePathUtil filePathUtil = new FilePathUtil();
  public enum Status {
    Create_Dirtory_Faild,       //创建失败
    Create_Dirtory_Success,     //创建成功
    Upload_NewFile_Faild,       //上传文件失败
    Upload_NewFile_Success,     //上传文件成功
    File_Exits,                 //文件已存在
    Local_File_NOT_Exits,       //本地文件不存在
    Remote_File_NOT_Exits,      //本地文件不存在
    Remote_Path_NOT_Exits,      //本地文件不存在
    Remote_Bigger_Local,        //远程文件大于本地文件
    Upload_From_Break_Faild,    //断点续传失败
    Upload_From_Break_Success,  //断点续传成功
    Delete_Remote_File_Faild,   //删除远程文件失败
    Success,
  }

  public FTPClientHelper()throws Exception{
    ftpClientPool = new FtpClientPool();
    ftpClientPool.init(new FtpPoolConfig());
  }



  public  String getKey(String userName,String password){
        return userName+"_"+password;
  }

  public boolean removeDiretory(String key,String remoteName) throws Exception {
    FTPClient ftpClient = null;
    try {
      ftpClient  = reInit(key);
      return ftpClient.removeDirectory(remoteName);
    }finally {
      ftpClientPool.returnBean(key,ftpClient);
    }
  }


  public boolean makedir(String remote,String key)throws Exception{
    FTPClient  ftpClient= null;
    try {
      ftpClient  = ftpClientPool.borrowBean(key);
    remote = new String(remote.getBytes(LOCAL_CHARSET), SERVER_CHARSET);
    if (remote.contains("/")) {
      String dirtory = remote.substring(0, remote.lastIndexOf("/") + 1);
      if (!dirtory.equalsIgnoreCase("/") && !ftpClient.changeWorkingDirectory(dirtory)) {  // 工作路径切换
        int start = 0;
        int end = 0;
        int changeNumber = 0;
        if (dirtory.startsWith("/")) {
          start = 1;
        }
        end = dirtory.indexOf("/", start);
        while (true) {
          String dir = remote.substring(start, end);
          if (!ftpClient.changeWorkingDirectory(dir)) {
            if (ftpClient.makeDirectory(dir)) {
              ftpClient.changeWorkingDirectory(dir);
              log.info(Status.Create_Dirtory_Success + ":dir");
            } else {
              log.info("无法创建新的目录");
              changeNumber++;
              if (changeNumber > 1) {
                return false;
              }
              continue;
            }
          }
          start = end + 1;
          end = dirtory.indexOf("/", start);
          if (start >= end) {
            break;
          }
        }

      }
      return true;
    }
    return false;
    }finally {
      ftpClientPool.returnBean(key,ftpClient);
    }
  }








  /*
    @Detail 上传文件
   */
  public Status uploadFile(String remote, InputStream in,String key)throws Exception{
    FTPClient  ftpClient= null;
    Status resulttatues = null;
    String remoteFilName = remote;
    if (remote.contains("/")) {
      remoteFilName = remote.substring(remote.lastIndexOf("/") + 1);
    }
    //断点续传部分
    try {
      ftpClient  = reInit(key);
      FTPFile[] ftpFile = ftpClient.listFiles(new String(remoteFilName.getBytes(LOCAL_CHARSET), SERVER_CHARSET));
        if (ftpFile.length >= 1) {
        log.info("该文件已存在");
        long localsize = in.available();
        long remotesize = ftpFile[0].getSize();
        if (remotesize == localsize) {
          throw new RuntimeException(Status.File_Exits + "");
        } else if (remotesize > localsize) {
          return Status.Remote_Bigger_Local;
        }
        //断点续传
        if (in.skip(remotesize) == remotesize) {
          ftpClient.setRestartOffset(remotesize);
          if (ftpClient.storeFile(remote, in)) {
            return Status.Upload_From_Break_Success;
          }
        }
      } else {
        //没有文件状态
        log.info(Status.Upload_From_Break_Faild.toString());
//        //TODO 不需要相同删除
//        if (!ftpClient.deleteFile(remote)) {
//          return Status.Delete_Remote_File_Faild;
//        }
        if (ftpClient.storeFile(new String(remote.getBytes(LOCAL_CHARSET), SERVER_CHARSET), in)) {
          resulttatues = Status.Upload_NewFile_Success;
        } else {
          resulttatues = Status.Upload_NewFile_Faild;
        }
      }
    }finally {
     ftpClientPool.returnBean(key,ftpClient);
     in.close();
    }
    return resulttatues;
  }


  /*
    @Detail 下载多个文件
   */
  public InputStream groupDownload(String remoteFilePath ,String remoteFileName ,String localFileName,String key) throws Exception {
    FTPClient ftpClient= null;
    InputStream inputStream  = null;
    try {
      String  path = new String((remoteFilePath).getBytes(LOCAL_CHARSET), SERVER_CHARSET);
      ftpClient  = reInit(key);
//      if(!ftpClient.changeWorkingDirectory(path)){
//        return Status.Remote_File_NOT_Exits;
//      }
//      FTPFile[] ftpFile = ftpClient.listFiles(new String((remoteFilePath+remoteFileName).getBytes(LOCAL_CHARSET), SERVER_CHARSET));
//      if (ftpFile.length < 1) {
//        return null;
//      }
      inputStream = ftpClient.retrieveFileStream(new String((remoteFilePath+remoteFileName).getBytes("utf-8"), SERVER_CHARSET));
    }finally {
      ftpClientPool.returnBean(key,ftpClient);
    }
    return inputStream;
  }





  /*
    @Detail 下载文件
   */
  public Status download(String remoteFilePath ,String remoteFileName ,String localFileName,String key,OutputStream out) throws Exception {
    FTPClient ftpClient= null;
    try {
    String  path = new String((remoteFilePath+remoteFileName).getBytes(LOCAL_CHARSET), SERVER_CHARSET);
      ftpClient  = reInit(key);
//      if(!ftpClient.changeWorkingDirectory(path)){
//        return Status.Remote_File_NOT_Exits;
//      }
      FTPFile[] ftpFile = ftpClient.listFiles(remoteFilePath);
      if (ftpFile.length < 1) {
        return Status.Remote_File_NOT_Exits;
      }
//      File file = new File("E:/"+localFileName);
//      outs = new FileOutputStream(file);
      Boolean b   = ftpClient.retrieveFile(new String((remoteFilePath+remoteFileName).getBytes(LOCAL_CHARSET), SERVER_CHARSET),out);
      out.flush();
    }finally {
      out.close();
      ftpClientPool.returnBean(key,ftpClient);
    }
    return Status.Success ;
  }





  public Boolean deleteFile(String remotename,String key)throws Exception{
    FTPClient ftpClient= null;
    try {
      ftpClient  = reInit(key);
      return ftpClient.deleteFile(remotename);
    }finally {
      ftpClientPool.returnBean(key,ftpClient);
    }
  }



  public List<FTPFile>getFTPFileList(String path,String key){
    FTPClient ftpClient= null;
    List<FTPFile>files = null;
    try {
      ftpClient  = reInit(key);
      files = Arrays.asList(ftpClient
          .listFiles(new String(path.getBytes(LOCAL_CHARSET), SERVER_CHARSET)));
    } catch (Exception e) {
      log.info(e.getMessage());
    }finally {
      ftpClientPool.returnBean(key,ftpClient);
    }
    return files;
  }


  public FTPClient reInit(String key) throws Exception {
    FTPClient  ftpClient= null;
    try {
      ftpClient = ftpClientPool.borrowBean(key);
    }catch (Exception e){
      ftpClientPool.addObject(key);
      ftpClient = ftpClientPool.borrowBean(key);
    }
    return  ftpClient;
  }



}