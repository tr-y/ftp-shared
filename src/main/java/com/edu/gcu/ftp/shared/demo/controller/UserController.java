package com.edu.gcu.ftp.shared.demo.controller;

import com.edu.gcu.ftp.shared.demo.config.FtpPoolConfig;
import com.edu.gcu.ftp.shared.demo.entity.User;
import com.edu.gcu.ftp.shared.demo.entity.UserFtp;
import com.edu.gcu.ftp.shared.demo.ftp.FTPClientHelper;
import com.edu.gcu.ftp.shared.demo.service.UserService;
import com.edu.gcu.ftp.shared.demo.util.ResponseUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.ibatis.io.ResolverUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/user")
public class UserController {


  @Autowired
  UserService userService;
  @Autowired
  FTPClientHelper ftpClientHelper;

  @ResponseBody
  @PostMapping(value = "login")
  public Map login(@RequestParam(value = "userId") String userId,
      @RequestParam(value = "password") String password,HttpServletRequest request) {
    Map resultMap = new HashMap();
    User user = userService.selectUser(userId, password);
    if (user !=null ) {
      resultMap.put("user",user);
      UserFtp userFtp = user.getUserFtp() ;
      if(userFtp!=null) {
        request.getSession().setAttribute("key",ftpClientHelper.getKey(userFtp.getFtpId(),userFtp.getFtp_pswd()));
      }
      return ResponseUtils.buildSuccessResponse(resultMap);
    }
    resultMap.put("user",null);
    return ResponseUtils.buildErrorResponse("账号和密码不存在或错误");
  }

  @ResponseBody
  @PostMapping(value = "register")
  public Map register(/*@RequestParam(value = "user")*/@RequestBody  User user) {
    int state = userService.isExist(user.getUserId());
    if (state == -1) {
      try {
        userService.addUser(user);
      } catch (RuntimeException e) {
        return ResponseUtils.buildErrorResponse("添加失败");
      }
    } else {
      return ResponseUtils.buildErrorResponse("存在该用户");
    }
    return ResponseUtils.buildSuccessResponse("success");
  }


  @ResponseBody
  @PostMapping(value = "joinfgroup")
  public Map joinFGroup(@RequestParam(value = "userId") Integer userId,
      @RequestParam(value = "gropId") Integer gropId) {
    try {
      userService.updateUserById(userId, gropId);
    } catch (Exception e) {
      return ResponseUtils.buildErrorResponse("修改组操作失败");
    }
    return ResponseUtils.buildSuccessResponse(null);
  }


  @ResponseBody
  @GetMapping(value = "listGroup")
  public Map  findListByGroupId(HttpServletRequest httpServletRequest,@RequestParam(value = "groupId") int groupId){

    List list = userService.findListByGroupId(groupId);
    if(list != null){
      return ResponseUtils.buildSuccessResponse(list);
    }
    return ResponseUtils.buildNullResponse();
  }







  @ResponseBody
  @PutMapping(value = "update")
  public Map  update(User user){
    try {
      userService.update(user);
    }catch (RuntimeException e){
      return ResponseUtils.buildErrorResponse("更新失败");
    }
    return ResponseUtils.buildSuccessResponse("success");

}
//    if(list != null){
//      return ResponseUtils.buildSuccessResponse(list);
//    }
//    return ResponseUtils.buildNullResponse();
}