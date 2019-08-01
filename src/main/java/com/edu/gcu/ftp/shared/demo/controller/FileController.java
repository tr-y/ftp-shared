package com.edu.gcu.ftp.shared.demo.controller;

import com.edu.gcu.ftp.shared.demo.entity.FileUser;
import com.edu.gcu.ftp.shared.demo.entity.FtpFile;
import com.edu.gcu.ftp.shared.demo.entity.UserFtp;
import com.edu.gcu.ftp.shared.demo.ftp.FTPClientHelper;
import com.edu.gcu.ftp.shared.demo.ftp.FTPClientHelper.Status;
import com.edu.gcu.ftp.shared.demo.service.FTPFileService;
import com.edu.gcu.ftp.shared.demo.service.FileUserService;
import com.edu.gcu.ftp.shared.demo.util.FTPUtil;
import com.edu.gcu.ftp.shared.demo.util.ResponseUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping(value = "/files")
public class FileController {

  private static Logger log = LoggerFactory.getLogger(FileController.class);
  @Autowired
  FTPFileService ftpFileService;

  @Autowired
  FTPClientHelper ftpClientHelper;


  @Autowired
  FileUserService fileUserService;





  @ResponseBody
  @PostMapping(value = "uploadsplit")
  public Map uploadSplitFile(HttpServletRequest httpServletRequest,
      @RequestParam("uploadFile") MultipartFile uploadFile
      , @RequestParam("fileUser") FileUser fileUser,
      @RequestParam(value = "userFtp") UserFtp userFtp){
    HttpSession session = httpServletRequest.getSession();
    String  key = (String) session.getAttribute("key");
    if(key == null){
      return ResponseUtils.buildNullResponse();
    }
    List list = (List)session.getAttribute("userList");
    try {
      if(list.isEmpty()){
        return  ResponseUtils.buildNullResponse();
      }
      InputStream in = null;
      in = uploadFile.getInputStream();
      ftpClientHelper.uploadFile(fileUser.getPath() + "/" + fileUser.getName(), in,key);
      fileUserService.insertFileUser(fileUser);
    } catch (Exception e) {
      return ResponseUtils.buildErrorResponse(e.getMessage());
    }
    return ResponseUtils.buildSuccessResponse("success");
  }



  @ResponseBody
  @DeleteMapping(value = "/delete")
  public Map deleteUserFile(HttpServletRequest httpServletRequest,
     @RequestParam(value = "name") String name) {
    try {
      String  key = (String) httpServletRequest.getSession().getAttribute("key");
      if(ftpClientHelper.deleteFile( "/" + name,key)){
        return  ResponseUtils.buildSuccessResponse("success");
      }
    } catch (RuntimeException e) {
      return ResponseUtils.buildErrorResponse(e.getMessage());
    } catch (Exception e) {
      return ResponseUtils.buildErrorResponse(e.getMessage());
    }
//    try {
//      ftpClientHelper.deleteFile( "/" + name,"201510098241_31415925");
//    } catch (Exception e1) {
//      e1.printStackTrace();
//    }
    return ResponseUtils.buildSuccessResponse("success");
  }








  //用户删除分成以下情况
  //1.用户总会分成三个存储
  //2.如果大于1可以直接删除
  //3.如果小于等于1需要将整个模块删除
  //4.其他组只是单纯引用，获取数据的时候只是单纯的获取其中一个，并将值合并获取
  // TODO 合并操作
//
//  @ResponseBody
//  @PostMapping(value = "/delete")
//  public Map deleteFile(HttpServletRequest request) {
//
//     return null;
//  }

//  @ResponseBody
//  @PostMapping(value = "gets")
//  public void getTest(HttpServletResponse response) {
//    FilePathUtil filePathUtil = new FilePathUtil();
//    String filename = "a.rar";
//    try {
//      response.setContentType("multipart/form-data");
//      response.addHeader("Content-Disposition",
//          "attachment; filename=\"" + new String(filename.getBytes("GBK"), "ISO8859-1") + "\"");
//    } catch (UnsupportedEncodingException e) {
//      e.printStackTrace();
//    }
//    try {
////      File  file  = new File( filePathUtil.getstaticpath().getAbsolutePath()+"/static/1.txt");
//      FTPUtil ftpUtil = new FTPUtil();
//      ftpUtil.connect("10.5.1.5", 21, "201510098241", "31415926");
//      OutputStream out = new BufferedOutputStream(response.getOutputStream());
//      ftpUtil.test("/a.rar", out);
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//    System.out.println("测试");
//  }


  @ResponseBody
  @GetMapping(value = "/gets")
  public Map get(int id,int parent,String path){
    Map resultMap = new HashMap();
    List<FileUser> fileUsers = null;
    List<FTPFile> ftpFiles = null;
    String key ="201510098241_31415926";

    try {
      ftpFiles = ftpClientHelper.getFTPFileList(path,key);
//        fileUserService.insertFTPFile(files);
    } catch (Exception e) {
      return  ResponseUtils.buildErrorResponse(e.getMessage());
    }
    if (ftpFiles != null) {
      resultMap.put("files",ftpFiles);
      return  ResponseUtils.buildSuccessResponse(resultMap);
    }

    else {
      resultMap.put("files",ftpFiles);
      return  ResponseUtils.buildSuccessResponse(resultMap);
    }
  }






  @ResponseBody
  @GetMapping(value = "/getDirList")
  public Map getDirList(HttpServletRequest request,
      @RequestParam(value = "id") int id, @RequestParam(value = "parent") int parent,
      @RequestParam(value = "path") String  path) {
    Map resultMap = new HashMap();
    List<FTPFile> ftpFiles = null;
    String key ="201510098241_31415926";//(String)request.getSession().getAttribute("key");
//    if(key == null){
//      return ResponseUtils.buildNullResponse();
//    }
//    List<FileUser> fileUsers = fileUserService.findListById(id, parent);
    List<FileUser> fileUsers = null;
//    resultMap.put("fileUsers",fileUsers);
//    if (fileUsers.isEmpty()) {
    try {
      ftpFiles = ftpClientHelper.getFTPFileList(path,key);
//        fileUserService.insertFTPFile(files);
    } catch (Exception e) {
      return  ResponseUtils.buildErrorResponse(e.getMessage());
    }
    if (ftpFiles != null) {
      resultMap.put("files",ftpFiles);
      return  ResponseUtils.buildSuccessResponse(resultMap);
    }
//    }
    else {
      resultMap.put("files",ftpFiles);
      return  ResponseUtils.buildSuccessResponse(resultMap);
    }
//    return ResponseUtils.buildNullResponse();
  }





}
