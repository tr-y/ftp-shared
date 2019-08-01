package com.edu.gcu.ftp.shared.demo.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.ResourceUtils;

public class FilePathUtil {

  File localpath  = null;
  File staticpath = null;

   void FilePathUtil(){
     try {
       localpath = new File(ResourceUtils.getURL("classpath:").getPath());
       if(staticpath == null){
         File  path = new File(localpath.getAbsoluteFile()+"/static/downloadFile");
         if(!path.exists()){
           path.mkdirs();
         }
       }
         staticpath  = new File(localpath.getAbsoluteFile()+"/static/downloadFile");
     } catch (FileNotFoundException e) {
       e.printStackTrace();
     }


   }



  public File  getstaticpath(){
      return  staticpath;
  }






}
