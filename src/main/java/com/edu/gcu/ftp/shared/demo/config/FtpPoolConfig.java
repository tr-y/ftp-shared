package com.edu.gcu.ftp.shared.demo.config;

import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;

public class FtpPoolConfig extends GenericKeyedObjectPoolConfig {

  private  String host = "123.207.41.205";

  private static int port =21;

  private int connectTimeOut =5000;

  private int bufferSize = 1024;

  private int fileType =2;

  private int dataTimeOut =12000 ;

  private boolean userEpsVWithIpv4 = false;

  private boolean passiveMode =true;

  private long timeBetweenEvictionRunsMillis = 10;

  private int minIdlePerKey = 0;

  private int maxIdlePerKey = 3;

  private int maxTotalPerKey =10 ;


  private String userName;

  private String password;



  public String getPassword() {
    return password;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getHost() {
    return host;
  }


  public void setHost(String host) {
    this.host = host;
  }

  public int getPort() {
    return port;
  }


  public void setPort(int port) {
    this.port = port;
  }

  public int getConnectTimeOut() {
    return connectTimeOut;
  }

  public void setConnectTimeOut(int connectTimeOut) {
    this.connectTimeOut = connectTimeOut;
  }

  public int getBufferSize() {
    return bufferSize;
  }

  public void setBufferSize(int bufferSize) {
    this.bufferSize = bufferSize;
  }

  public int getFileType() {
    return fileType;
  }

  public void setFileType(int fileType) {
    this.fileType = fileType;
  }

  public int getDataTimeOut() {
    return dataTimeOut;
  }

  public void setDataTimeOut(int dataTimeOut) {
    this.dataTimeOut = dataTimeOut;
  }

  public boolean isUserEpsVWithIpv4() {
    return userEpsVWithIpv4;
  }

  public void setUserEpsVWithIpv4(boolean userEpsVWithIpv4) {
    this.userEpsVWithIpv4 = userEpsVWithIpv4;
  }

  public boolean isPassiveMode() {
    return passiveMode;
  }

  public void setPassiveMode(boolean passiveMode) {
    this.passiveMode = passiveMode;
  }
}
