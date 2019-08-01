package com.edu.gcu.ftp.shared.demo.controller;

import com.edu.gcu.ftp.shared.demo.entity.FileUser;
import com.edu.gcu.ftp.shared.demo.entity.UserFtp;
import com.edu.gcu.ftp.shared.demo.ftp.FTPClientHelper;
import com.edu.gcu.ftp.shared.demo.ftp.FTPClientHelper.Status;
import com.edu.gcu.ftp.shared.demo.service.FileUserService;
import com.edu.gcu.ftp.shared.demo.util.FTPUtil;
import com.edu.gcu.ftp.shared.demo.util.ResponseUtils;
import com.sun.imageio.spi.OutputStreamImageOutputStreamSpi;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/fileUser")
public class FileUserController {

  @Autowired
  private FTPClientHelper ftpClientHelper ;



  @Autowired
  FileUserService fileUserService;

  @ResponseBody
  @PostMapping(value = "buildFile")
  public Map buildFile(@RequestBody FileUser fileUser) {

    if (fileUserService.insertFileUser(fileUser) > 0) {
     return   ResponseUtils.buildSuccessResponse("success");
    }
    return ResponseUtils.buildErrorResponse("error");
  }


  @ResponseBody
  @GetMapping(value = "search")
  public Map  search(@RequestParam(value = "order")String order,@RequestParam(value = "userId")int userId,int state){
    List list = fileUserService.searchByOrder(order,userId,state);
    if(list != null){
      return   ResponseUtils.buildSuccessResponse(list);
    }
      return ResponseUtils.buildNullResponse();
  }


  @ResponseBody
  @GetMapping(value = "getByParentId")
  public Map  getById(@RequestParam(value = "id")int parentId){
    try {
      int result = fileUserService.getById(parentId);
      return ResponseUtils.buildSuccessResponse(result);
    }catch (RuntimeException e){
      return ResponseUtils.buildErrorResponse("error");
    }
  }




  @ResponseBody
  @PostMapping(value = "add")
  public Map  add(@RequestBody FileUser fileUser){
    try {
      fileUserService.insertFileUser(fileUser);
    }catch (RuntimeException e){
      return ResponseUtils.buildErrorResponse("error");
    }
    return  ResponseUtils.buildSuccessResponse("success");
  }

  @ResponseBody
  @DeleteMapping(value = "/delete")
  public Map  delete(@RequestParam(value = "id") int id,@RequestParam(value = "userId")int userId,@RequestParam(value = "parentId")int parentId){
    try {
      fileUserService.delete(id,userId,parentId);
    }catch (RuntimeException e){
      return ResponseUtils.buildErrorResponse("error");
    }
    return  ResponseUtils.buildSuccessResponse("success");
  }





  @ResponseBody
  @GetMapping(value = "getListUser")
  public Map findListByUserId(@RequestParam(value = "userId") int userId,
      @RequestParam(value = "parentId") int parentId,HttpServletRequest request) {
//    request.getSession().setAttribute("key","201510098241_31415926");
    List list = fileUserService.findListById(userId, parentId);
    if (list != null) {
      return ResponseUtils.buildSuccessResponse(list);
    }
    return ResponseUtils.buildNullResponse();
  }


  @ResponseBody
  @PostMapping(value = "upload")
  public Map uploadFile(HttpServletRequest httpServletRequest,
        @RequestParam(value = "uploadFile")MultipartFile uploadFile,
        @RequestParam(value = "fileName")  String fileName) {
    HttpSession session = httpServletRequest.getSession();
    try {
      String key = (String) session.getAttribute("key");
      InputStream in = null;
      in = uploadFile.getInputStream();
      ftpClientHelper.uploadFile( "/" + fileName, in,key);
    } catch (Exception e) {
      return ResponseUtils.buildErrorResponse(e.getMessage());
    }
    return ResponseUtils.buildSuccessResponse("success");
  }





  @ResponseBody
  @PutMapping(value = "update")
  public Map update(@RequestParam(value = "fileUser")FileUser fileUser) {
  try {
    fileUserService.updateByPrimaryKeyWithBLOBs(fileUser);
  }catch (RuntimeException e){
    return ResponseUtils.buildErrorResponse(e.getMessage());
  }
    return ResponseUtils.buildSuccessResponse("success");

  }


  @ResponseBody
  @GetMapping(value = "/isNotExist")
  public Map  isNotExist(@RequestParam(value = "name")String name,@RequestParam(value = "userId") int userId,
      @RequestParam(value = "parentId")int parentId){
    if( !fileUserService.findByName(name,userId,parentId)){
      return ResponseUtils.buildSuccessResponse("success");
    }
    return ResponseUtils.buildErrorResponse(" not null");
  }



  @ResponseBody
  @GetMapping(value = "download")
  public Map downloadFile(HttpServletRequest request,@RequestParam(value="name")String name,HttpServletResponse httpServletResponse) {
    String key = (String)request.getSession().getAttribute("key");
    if(key == null){
      return ResponseUtils.buildNullResponse();
    }
    try {
      httpServletResponse.setContentType("application/multipart/form-data");
      httpServletResponse.setHeader("Content-Dispostion","attachment;filename="+name);
      OutputStream out = httpServletResponse.getOutputStream();
      FTPClientHelper.Status status = ftpClientHelper.download("/", name,  name,key,out);
      if(status == Status.Success ){
        return ResponseUtils.buildSuccessResponse("success");
      }
    } catch (Exception e) {
    return ResponseUtils.buildErrorResponse(e.getMessage());
    }
    return ResponseUtils.buildErrorResponse("error");
  }




//
//  public FTPUtil getFtpUtil(String id, String password, FTPUtil ftpUtil) throws IOException {
//    if (ftpUtil == null) {
//      ftpUtil = new FTPUtil();
//      try {
//        if (ftpUtil.connect(id, password)) {
//          return ftpUtil;
//        }
//      } catch (IOException e) {
//        throw e;
//      }
//    }
//    return ftpUtil;
//  }


}