package com.edu.gcu.ftp.shared.demo.ftp;

import com.edu.gcu.ftp.shared.demo.config.FtpPoolConfig;
import com.edu.gcu.ftp.shared.demo.util.FTPUtil;
import java.io.IOException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FtpClientFactory extends BaseKeyedPooledObjectFactory<String,FTPClient> {

  private static String LOCAL_CHARSET   = "GBK";
  private static String SERVER_CHARSET  = "ISO-8859-1";
  private static String CONTROL_CHARSET =" utf-8";


  private FtpPoolConfig ftpPoolConfig;
  private static Logger log = LoggerFactory.getLogger(FTPUtil.class);

  public FtpPoolConfig getFtpPoolConfig() {
    return ftpPoolConfig;
  }

  public void setFtpPoolConfig(FtpPoolConfig ftpPoolConfig) {
    this.ftpPoolConfig = ftpPoolConfig; }


  //服务器超时
  @Override
  public boolean validateObject(String key, PooledObject<FTPClient> p) {
    FTPClient ftpClient = p.getObject();
    try {
      if(ftpClient.sendNoOp()){
        return true;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }


  @Override
  public void destroyObject(String key, PooledObject<FTPClient> p) throws Exception {
    FTPClient ftpClient = p.getObject();
    ftpClient.logout();
    super.destroyObject(key,p);
  }

  @Override
  public FTPClient create(String key) throws Exception {
    FTPClient ftpClient = new FTPClient();
    ftpClient.connect(ftpPoolConfig.getHost(), ftpPoolConfig.getPort());
    ftpClient.setConnectTimeout(ftpClient.getConnectTimeout());
    if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
      log.info("拒绝连接");
      disconnect(ftpClient);
      return null;
    }//登陆成功或失败
    String[] params  = key.split("_");
    if (!ftpClient.login(params[0],params[1])){
      throw new RuntimeException("ftp登陆失败");
    }
    ftpClient.setControlEncoding(SERVER_CHARSET);
    ftpClient.setBufferSize(ftpPoolConfig.getBufferSize());
    ftpClient.setDataTimeout(ftpPoolConfig.getDataTimeOut());
    ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
    ftpClient.setRemoteVerificationEnabled(false);
    if(ftpPoolConfig.isPassiveMode()){
      log.info("进入ftp被动模式");
      ftpClient.enterLocalPassiveMode();
    }
    return ftpClient;
  }

  @Override
  public PooledObject<FTPClient> wrap(FTPClient ftpClient) {
    return new DefaultPooledObject<FTPClient>(ftpClient);
  }


  @Override
  public void activateObject(String key, PooledObject<FTPClient> p) throws Exception {
    super.activateObject(key, p);
  }

  @Override
  public void passivateObject(String key, PooledObject<FTPClient> p) throws Exception {
    super.passivateObject(key, p);
  }

  public void disconnect(FTPClient ftpClient) throws IOException {
    if (ftpClient.isConnected()) {
      ftpClient.disconnect();
    }
  }

}
