package com.edu.gcu.ftp.shared.demo.controller;

import com.edu.gcu.ftp.shared.demo.entity.FtpFile;
import com.edu.gcu.ftp.shared.demo.service.FTPFileService;
import com.edu.gcu.ftp.shared.demo.util.ResponseUtils;
import java.io.IOException;
import java.util.Map;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.annotation.QueryAnnotation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "ftpfile")
public class FTPFileController {



  @Autowired
  FTPFileService ftpFileService;

  //fileId为文件的md5值
  @ResponseBody
  @GetMapping(value = "isExist")
  public Map fileIsExist(@RequestParam(value = "fileId") String fileId) {
    int ftpFileId ;
    try {
      ftpFileId = ftpFileService.findFileByUserId(fileId);
      if (ftpFileId>0) {
        return ResponseUtils.buildSuccessResponse(ftpFileId);
      }
    } catch (RuntimeException e) {
      return ResponseUtils.buildErrorResponse(e.getMessage());
    }
    return ResponseUtils.buildNullResponse();
  }



  @ResponseBody
  @PostMapping(value = "add")
  public Map addFtpFile( FtpFile ftpFile) {
    int fileId;
    try {
     fileId  = ftpFileService.insertFile(ftpFile);
  }catch (RuntimeException e ){
    return ResponseUtils.buildErrorResponse(e.getMessage());
  }
    return ResponseUtils.buildSuccessResponse(fileId);
  }


  @ResponseBody
  @PutMapping(value = "update")
  public Map update(@RequestParam(value = "ftpFile") FtpFile ftpFile) {
    try {
     ftpFileService.update(ftpFile);
    }catch (RuntimeException e ){
      return ResponseUtils.buildErrorResponse(e.getMessage());
    }
    return ResponseUtils.buildSuccessResponse("success");
  }


  @ResponseBody
  @DeleteMapping(value = "delete")
  public Map delete(@RequestParam(value = "fileId") int fileId) {
    try {
      ftpFileService.delete(fileId);
    }catch (RuntimeException e ){
      return ResponseUtils.buildErrorResponse(e.getMessage());
    }
    return ResponseUtils.buildSuccessResponse("success");
  }




}
