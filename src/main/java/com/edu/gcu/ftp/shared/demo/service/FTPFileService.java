package com.edu.gcu.ftp.shared.demo.service;


import com.edu.gcu.ftp.shared.demo.entity.FtpFile;


public interface FTPFileService {

  int insertFile(FtpFile ftpFile);

  int  findFileByUserId(String fileId);

  void update( FtpFile ftpFile);

  void delete(int fileId);
}
