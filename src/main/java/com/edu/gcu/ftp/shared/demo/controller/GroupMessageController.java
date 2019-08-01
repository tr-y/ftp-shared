package com.edu.gcu.ftp.shared.demo.controller;

import com.edu.gcu.ftp.shared.demo.entity.GroupMessage;
import com.edu.gcu.ftp.shared.demo.service.GroupMessageService;
import com.edu.gcu.ftp.shared.demo.util.ResponseUtils;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/groupMessage")
public class GroupMessageController {


  @Autowired
  GroupMessageService groupMessageService;

  @ResponseBody
  @GetMapping(value = "list")
  public Map getList(@RequestParam(value = "groupId") int groupId) {

    List list = groupMessageService.findListByGroupId(groupId);
    if (list.isEmpty()) {
      return ResponseUtils.buildNullResponse();
    }
    return ResponseUtils.buildSuccessResponse(list);

  }


  @ResponseBody
  @PostMapping(value = "add")
  public Map addMessage(@RequestBody GroupMessage groupMessage) {
    try {
      groupMessageService.addMessage(groupMessage);
    } catch (RuntimeException e) {
      return ResponseUtils.buildErrorResponse("error");
    }
    return ResponseUtils.buildSuccessResponse("success");

  }

  @ResponseBody
  @DeleteMapping(value = "delete")
  public Map addMessage(@RequestParam(value = "id")int id) {
    try {
      groupMessageService.delete(id);
    } catch (RuntimeException e) {
      return ResponseUtils.buildErrorResponse("error");
    }
    return ResponseUtils.buildSuccessResponse("success");

  }


}
