package com.edu.gcu.ftp.shared.demo.controller;

import com.edu.gcu.ftp.shared.demo.entity.FGroup;
import com.edu.gcu.ftp.shared.demo.service.FGroupService;
import com.edu.gcu.ftp.shared.demo.util.ResponseUtils;
import java.util.HashMap;
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
@RequestMapping(value = "/fgroup")
public class FGroupController {

  @Autowired
  FGroupService fGroupService;


  @ResponseBody
  @PostMapping(value = "/build")
  public Map buildFGroup(@RequestBody FGroup fGroup) {
    Integer fgroupid = null;
    try {
      fgroupid =  fGroupService.addFGroup(fGroup);
    } catch (Exception e) {
      return ResponseUtils.buildErrorResponse(e.getMessage());
    }
    return ResponseUtils.buildSuccessResponse(fgroupid);
  }


  @ResponseBody
  @GetMapping(value = "/search")
  public Map searchByName(@RequestParam(value = "name") String name) {

    List list = fGroupService.searchByName(name);
    if (list != null) {
      return ResponseUtils.buildSuccessResponse(list);
    }
    return ResponseUtils.buildNullResponse();
  }



  @ResponseBody
  @GetMapping(value = "/find")
  public Map findList() {
    List list = fGroupService.findList();
    if (list != null) {
      return ResponseUtils.buildSuccessResponse(list);
    }
    return ResponseUtils.buildNullResponse();
  }



//  @ResponseBody
//  @DeleteMapping(value = "/del")
//  public Map delete(@RequestParam(value = "id ") int id ) {
//    try {
//      fGroupService.delete(id);
//    }catch (RuntimeException e){
//      return ResponseUtils.buildErrorResponse(e.getMessage());
//    }
//    return ResponseUtils.buildSuccessResponse("success");
//  }





}
