package com.edu.gcu.ftp.shared.demo.controller;

import com.edu.gcu.ftp.shared.demo.entity.UserGroup;
import com.edu.gcu.ftp.shared.demo.service.UserGroupService;
import com.edu.gcu.ftp.shared.demo.util.ResponseUtils;
import java.util.List;
import java.util.Map;
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

@Controller
@RequestMapping(value = "userGroup")
public class UserGroupController {

  @Autowired
  UserGroupService userGroupService;

  @ResponseBody
  @PostMapping(value = "add")
  public Map addUserGroup(UserGroup userGroup ){
    try {
      userGroupService.add(userGroup);
    }catch (RuntimeException e){
      return ResponseUtils.buildErrorResponse("error");
    }
    return ResponseUtils.buildSuccessResponse("success");
  }


  @ResponseBody
  @GetMapping(value = "list")
  public Map findList(@RequestParam(value = "groupId")int groupId,@RequestParam(value = "send")int send,
  @RequestParam(value = "userId")int userId){
    List list  = null;
    try {
     list  =   userGroupService.findList(groupId,send,userId);
    if(list == null){
      return ResponseUtils.buildNullResponse();
    }
    }catch (RuntimeException e){
      return ResponseUtils.buildErrorResponse("error");
    }
    return ResponseUtils.buildSuccessResponse(list);
  }


  @ResponseBody
  @DeleteMapping(value = "delete")
  public Map delete(@RequestParam(value = "id") int id){
    try {
      userGroupService.delete(id);
    }catch (RuntimeException e){
      return ResponseUtils.buildErrorResponse("error");
    }
    return ResponseUtils.buildSuccessResponse("success");
  }



  @ResponseBody
  @PutMapping(value = "update")
  public Map update( UserGroup userGroup){
    try {
      userGroupService.update(userGroup);
    }catch (RuntimeException e){
      return ResponseUtils.buildErrorResponse("error");
    }
    return ResponseUtils.buildSuccessResponse("success");
  }

}
