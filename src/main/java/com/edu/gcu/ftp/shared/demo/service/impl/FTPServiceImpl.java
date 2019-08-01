package com.edu.gcu.ftp.shared.demo.service.impl;

import com.edu.gcu.ftp.shared.demo.service.FTPService;

import java.net.Socket;


import org.springframework.stereotype.Service;
import sun.net.ftp.FtpClient;
@Service
public class FTPServiceImpl implements FTPService {

  @Override
  public FtpClient connectFTP(String url, int port, String username, String password) {

    return null;
  }

  @Override
  public void upload(String loadFile, String ftpFile, FtpClient ftp) {

  }

  @Override
  public void download(String loadfile, String ftpFile, FtpClient ftp) {

  }
}
