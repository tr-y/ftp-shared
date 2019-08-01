package com.edu.gcu.ftp.shared.demo.controller;

import com.edu.gcu.ftp.shared.demo.entity.FileSplit;
import com.edu.gcu.ftp.shared.demo.entity.FtpFile;
import com.edu.gcu.ftp.shared.demo.entity.User;
import com.edu.gcu.ftp.shared.demo.entity.UserFtp;
import com.edu.gcu.ftp.shared.demo.ftp.FTPClientHelper;
import com.edu.gcu.ftp.shared.demo.service.FileSplitService;
import com.edu.gcu.ftp.shared.demo.util.ResponseUtils;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MultipartFilter;

@Controller
@RequestMapping(value = "fileSplit")
public class FileSplitController {
  //TODO 获取文件以及被分片文件个数，更改文件属性值

  @Autowired
  FileSplitService fileSplitService;

  @Autowired
  FTPClientHelper ftpClientHelper;


  @RequestMapping(value = "list")
  @ResponseBody
  public Map getListByFileId(@RequestParam(value = "fileId") String filId) {
    List list = fileSplitService.findListByFileId(filId);
    if (list != null) {
      return ResponseUtils.buildSuccessResponse(list);
    }
    return ResponseUtils.buildNullResponse();

  }


  @RequestMapping(value = "upload")
  @ResponseBody
  public Map uploadSplitFile(HttpServletRequest httpServletRequest, @RequestParam("uploadFile") MultipartFile uploadFile
      ,@RequestParam(value = "fileSplit")FileSplit fileSplit){
    List<User> list =  (List)httpServletRequest.getSession().getAttribute("userList");
    int postion = fileSplit.getNumbering();
    if(list.isEmpty()||list.size()<postion||postion<0){
      return ResponseUtils.buildNullResponse();
    }
    UserFtp userFtp = list.get(postion).getUserFtp();
    String key = ftpClientHelper.getKey(userFtp.getFtpId(),userFtp.getFtp_pswd());
    InputStream in = null;
    try {
      in = uploadFile.getInputStream();
      ftpClientHelper.uploadFile("/", in, key);
      fileSplitService.insert( fileSplit);
    }catch (Exception e){
      return ResponseUtils.buildErrorResponse(e.getMessage());
    }

    return ResponseUtils.buildSuccessResponse("success");
  }


  @ResponseBody
  @DeleteMapping(value = "delete")
  public Map delete(@RequestParam(value = "id")int  id){
  try {
    fileSplitService.delete(id);
  }catch (RuntimeException e){
    return ResponseUtils.buildErrorResponse(e.getMessage());
  }
    return ResponseUtils.buildSuccessResponse("success");

  }



  @ResponseBody
  @DeleteMapping(value = "update")
  public Map update(@RequestParam(value = "fileSplit")FileSplit  fileSplit){
    try {
      fileSplitService.update(fileSplit);
    }catch (RuntimeException e){
      return ResponseUtils.buildErrorResponse(e.getMessage());
    }
    return ResponseUtils.buildSuccessResponse("success");
  }



  //将文件存储在本地，再将文件存放在其他地方
  //这部分有由管理员操作
  // TODO

  @ResponseBody
  @PostMapping(value = "move")
  public  Map moveFile() {
    return null;
  }




}
