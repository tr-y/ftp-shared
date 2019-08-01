package com.edu.gcu.ftp.shared.demo.service;

import org.springframework.stereotype.Service;
import sun.net.ftp.FtpClient;

public interface FTPService {

   FtpClient connectFTP(String url , int port ,String username ,String password);
   void upload(String loadFile ,String ftpFile,FtpClient ftp);
   void download(String loadfile , String ftpFile , FtpClient ftp);

}
