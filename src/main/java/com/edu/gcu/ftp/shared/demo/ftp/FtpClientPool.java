package com.edu.gcu.ftp.shared.demo.ftp;

import com.edu.gcu.ftp.shared.demo.config.FtpPoolConfig;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;


public class FtpClientPool {

  private GenericKeyedObjectPool<String ,FTPClient> pool;

  private FtpClientFactory ftpClientFactory;


  public void init(FtpPoolConfig ftpPoolConfig)throws Exception{
    if(pool==null) {
      this.ftpClientFactory = new FtpClientFactory();
      ftpClientFactory.setFtpPoolConfig(ftpPoolConfig);
      pool = new GenericKeyedObjectPool<String, FTPClient>(ftpClientFactory, ftpPoolConfig);
    }
  }

  public FtpClientFactory getFtpClientFactory(){
      return ftpClientFactory;
  }


  public GenericKeyedObjectPool<String, FTPClient> getPool() {
    return pool;
  }

  public FTPClient borrowBean(String key)throws  Exception{
    FTPClient  ftpClient  =  pool.borrowObject(key);
    return ftpClient;
  }


  public void addObject(String key){
    try {
      pool.addObject(key);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }





  public void returnBean(String key,FTPClient ftpClient){
    if(ftpClient != null){
      pool.returnObject(key,ftpClient);
    }
  }





}



