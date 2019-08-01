package com.edu.gcu.ftp.shared.demo.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class FTPUtil {

//
//  @Value("${ftp.host}")
//  private String host;
//
//  @Value("${ftp.port}")
//  private int  port;


  @Autowired
  RedisTemplate redisTemplate;

  private FTPClient    ftpClient = null;
  public  FilePathUtil filePathUtil = new FilePathUtil();
  private static  String  LOCAL_CHARSET = "GBK";
  private static  String  SERVER_CHARSET = "ISO-8859-1";

  private static Logger log = LoggerFactory.getLogger(FTPUtil.class);



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

  }

  public FTPClient getFtpClient(){
    return ftpClient;
  }

//  建立线程池
//  @Async(value="FTPThreadPool")
//  public Boolean connect(String username, String password)
//      throws IOException {
//    if(ftpClient == null){
//      ftpClient = new FTPClient();
//    }
//      ftpClient.connect(host, port);
//      ftpClient.enterLocalPassiveMode();
//      if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {   //登陆成功或失败
//        if (ftpClient.login(username, password)) {
//          if(FTPReply.isPositiveCompletion(ftpClient.sendCommand("OPTS UTF-8","ON"))){
//            LOCAL_CHARSET  = "utf-8";
//          }
//          ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
//          //        ftpClient.setRemoteVerificationEnabled(false);
//          ftpClient.setControlEncoding(LOCAL_CHARSET);
//          return true;
//        }
//      }
//    disconnect();
//    return false;
//  }
//
//
//
//  public void disconnect() throws IOException {
//    if (ftpClient.isConnected()) {
//      ftpClient.disconnect();
//    }
//  }

  public Status uploadFile(String remote, InputStream in)
      throws IOException {
    Status resulttatues = null;
    remote = new String(remote.getBytes(LOCAL_CHARSET), SERVER_CHARSET);
    String remoteFilName = remote;
    if (remote.contains("/")) {
      remoteFilName = remote.substring(remote.lastIndexOf("/") + 1);
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
                return Status.Create_Dirtory_Faild;
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
    }
    //断点续传部分
    FTPFile[] ftpFile = ftpClient.listFiles(remoteFilName);
//    File file = new File(local);
//    if (!file.exists()) {
//      return Status.Local_File_NOT_Exits;
//    }
//    InputStream in = new FileInputStream(file);
    if (ftpFile.length >= 1) {
      log.info("该文件已存在");
      long localsize = in.available();
      long remotesize = ftpFile[0].getSize();
      if (remotesize == localsize) {
       throw  new RuntimeException(Status.File_Exits+"");
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
    }else {
      //没有文件状态
      log.info(Status.Upload_From_Break_Faild.toString());
        if (!ftpClient.deleteFile(remoteFilName)) {
          return Status.Delete_Remote_File_Faild;
        }
      if (ftpClient.storeFile(remote, in)) {
        resulttatues = Status.Upload_NewFile_Success;
      } else {
        resulttatues = Status.Upload_NewFile_Faild;
      }
    }
    in.close();
    return resulttatues;
  }



  public Status download(String remoteFilePath ,String remoteFileName ,String LocalFilePath,String localFileName) throws IOException {
    String  path = new String((remoteFilePath).getBytes(LOCAL_CHARSET), SERVER_CHARSET);
    if(!ftpClient.changeWorkingDirectory(path)){
      return Status.Remote_File_NOT_Exits;
    }
    FTPFile[] ftpFile = ftpClient.listFiles(remoteFileName);
    if (ftpFile.length < 1) {
      return Status.Remote_File_NOT_Exits;
    }


//    if(file.exists()){
//      file.delete();
//      //TODO 文件在该处存在
//    }
    File file = new File(filePathUtil.getstaticpath().getAbsolutePath()+localFileName);
    OutputStream out = new FileOutputStream(file);
    ftpClient.retrieveFile(remoteFileName,out);
    out.close();
    return null;
  }



}