package com.edu.gcu.ftp.shared.demo.util;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.codec.digest.DigestUtils;


public class FileUtil {

  public static String getMd5(InputStream in){
    String md5 = null;
    try {
       md5 = DigestUtils.md5Hex(in);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return md5;
  }


}
