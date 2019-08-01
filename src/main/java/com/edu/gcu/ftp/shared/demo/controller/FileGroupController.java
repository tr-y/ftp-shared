package com.edu.gcu.ftp.shared.demo.controller;

import com.edu.gcu.ftp.shared.demo.entity.FileGroup;
import com.edu.gcu.ftp.shared.demo.entity.User;
import com.edu.gcu.ftp.shared.demo.entity.UserFtp;
import com.edu.gcu.ftp.shared.demo.ftp.FTPClientHelper;
import com.edu.gcu.ftp.shared.demo.ftp.FTPClientHelper.Status;
import com.edu.gcu.ftp.shared.demo.service.FileGroupService;
import com.edu.gcu.ftp.shared.demo.service.UserService;
import com.edu.gcu.ftp.shared.demo.util.ResponseUtils;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.SequenceInputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "/filegroup")
public class FileGroupController {

  @Autowired
  FileGroupService fileGroupService;

  @Autowired
  FTPClientHelper ftpClientHelper;

  @Autowired
  UserService userService;


  @ResponseBody
  @GetMapping(value = "find")
  public Map findByGroupIdAndParent(@RequestParam(value = "groupId") int groupId,
      @RequestParam(value = "parentId") int parent) {
    try {
      List list = fileGroupService.findByGroupIdAndParent(groupId, parent);
      if(!list.isEmpty()){
        return ResponseUtils.buildSuccessResponse(list);
      }
    } catch (RuntimeException e) {
      ResponseUtils.buildErrorResponse(e.getMessage());
    }
    return ResponseUtils.buildNullResponse();
  }


  @ResponseBody
  @GetMapping(value = "findByFileId")
  public Map findByFileIdAndUserId(@RequestParam(value = "fileId") int fileId,
      @RequestParam(value = "userId")int userId){
    try {
////      List list = fileGroupService.findByFileIdAndUserId(fileId, userId);
//      if(!list.isEmpty()){
//        return ResponseUtils.buildSuccessResponse(list);
//      }
    } catch (RuntimeException e) {
      ResponseUtils.buildErrorResponse(e.getMessage());
    }
    return ResponseUtils.buildNullResponse();
  }







  @ResponseBody
  @GetMapping(value = "search")
  public Map search(@RequestParam(value = "groupId") int groupId,
      @RequestParam(value = "state") int state, @RequestParam(value = "order") String order) {
    List list  =  null;
    try {
      list = fileGroupService.searchByOrder(groupId, order, state);
      if(list.isEmpty()){
        return ResponseUtils.buildNullResponse();
      }
    } catch (RuntimeException e) {
      ResponseUtils.buildErrorResponse(e.getMessage());
    }
    return ResponseUtils.buildSuccessResponse(list);
  }





  @ResponseBody
  @PostMapping(value = "add")
  public Map addFileGroup( @RequestBody FileGroup fileGroup) {
    try {
      fileGroupService.insertFileUser(fileGroup);
    } catch (RuntimeException e) {
      ResponseUtils.buildErrorResponse(e.getMessage());
    }
    return ResponseUtils.buildSuccessResponse("success");
  }





  @ResponseBody
  @PostMapping(value = "upload")
  public Map uploadGroupFile(HttpServletRequest httpServletRequest,
      @RequestParam(value = "uploadFile")MultipartFile  uploadFile,
      @RequestParam(value = "fileName")  String fileName,
      @RequestParam(value = "currentChunk")  int currentChunk,
      @RequestParam(value = "groupId")  int groupId,
      @RequestParam(value = "userId")  int userId,
      @RequestParam(value = "key")String key){
    Map  resultMap  = new HashMap();
    resultMap.put("userId",userId);
    resultMap.put("currentChunk",currentChunk);
    try {
      InputStream in = null;
      in  = uploadFile.getInputStream();
//      List<User> userList = (List)httpServletRequest.getSession().getAttribute("userList");
//      if(userList==null||userList.isEmpty()){
//         userList =  userService.findListByGroupId(groupId);
//      }
//      if(userList.isEmpty()){
//        return ResponseUtils.buildErrorResponse("没有合适的用户进行存储");
//      }
//      len  = userList.size();
//      UserFtp userFtp   = userList.get(len-1).getUserFtp();
//      if(userList.get(len-1).getShare_size()<uploadFile.getSize()){
//        return ResponseUtils.buildErrorResponse("用户当前的存储空间无法完全装载该文件");
//      }
//      String key  =ftpClientHelper.getKey(userFtp.getFtpId(),userFtp.getFtp_pswd());
      ftpClientHelper.uploadFile( "/" + fileName, in,key);
//      System.out.println(userList.get(len-1).getUserFtp().getFtp_pswd());
//      userList.remove(len-1);
//      httpServletRequest.getSession().setAttribute("userList",userList);
    } catch (Exception e) {
      return ResponseUtils.buildErrorResponse(e.getMessage());
    }
    return ResponseUtils.buildSuccessResponse(resultMap);
  }




  @ResponseBody
  @GetMapping(value = "download")
  public void  downloadFile(@RequestParam(value="name")String name,
      @RequestParam(value = "fileId")int fileId, HttpServletResponse httpServletResponse) {
    String key = null;
    List<InputStream> lisFile = null;
    byte[] b = new byte[1000 * 10];
    int end = 0;
    OutputStream out = null;
    try {
      httpServletResponse.setContentType("application/multipart/form-data");
      httpServletResponse.setHeader("Content-Dispostion", "attachment;filename=" + name);
      out = httpServletResponse.getOutputStream();
      List<UserFtp> list = fileGroupService.findListByFileId(fileId);
      lisFile = new ArrayList<>();
      InputStream in = null;
     if(list.isEmpty()){
       return;
     }
      int n = 0;
      for (UserFtp item : list) {
        key = item.getFtpId() + "_" + item.getFtp_pswd();
        try {
          lisFile.add(in=ftpClientHelper.groupDownload("/", name + n, name + n, key));
        } catch (Exception e) {
          e.printStackTrace();
        }
        n++;
      }
      Enumeration<InputStream> enumeration = Collections.enumeration(lisFile);
      SequenceInputStream sis = new SequenceInputStream(enumeration);
      while ((end = sis.read(b)) != -1) {
        out.write(b, 0, end);
      }
      out.flush();
    }catch (Exception e){
      return;
    }finally {
      try {
        out.close();
        for(InputStream item :lisFile){
          item.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

    //TODO 修改目录和文件夹
  @ResponseBody
  @PutMapping(value = "update")
  public Map updateFileGroup(@RequestParam(value = "filegroup") FileGroup fileGroup) {
    boolean isExist = false;
    try {
      isExist = fileGroupService.updateFileGroup(fileGroup);
    } catch (RuntimeException e) {
      ResponseUtils.buildErrorResponse(e.getMessage());
    }
    if (!isExist) {
      return ResponseUtils.buildNullResponse();
    }
    return ResponseUtils.buildSuccessResponse("success");

  }


  @ResponseBody
  @DeleteMapping(value = "delete")
  public Map deleteFileGroup(@RequestParam(value = "id") int id) {
    boolean isExist = false;
    try {
      isExist = fileGroupService.deleteFileGroup(id);
    } catch (RuntimeException e) {
      ResponseUtils.buildErrorResponse(e.getMessage());
    }
    if (!isExist) {
      return ResponseUtils.buildNullResponse();
    }
    return ResponseUtils.buildSuccessResponse("success");

  }


  @ResponseBody
  @GetMapping(value = "/isNotExist")
  public Map isNotExist(@RequestParam(value = "name") String name,
      @RequestParam(value = "groupId") int groupId,
      @RequestParam(value = "parentId") int parentId) {
    if (!fileGroupService.findByName(name, groupId, parentId)) {
      return ResponseUtils.buildSuccessResponse("success");
    }
    return ResponseUtils.buildErrorResponse(" not null");
  }


  @ResponseBody
  @GetMapping(value = "/add")
  public Map insert(@RequestBody FileGroup fileGroup) {
    try {
      fileGroupService.insertFileUser(fileGroup);
    } catch (RuntimeException e) {
      return ResponseUtils.buildErrorResponse(" not null");

    } return ResponseUtils.buildSuccessResponse("success");

  }


}
