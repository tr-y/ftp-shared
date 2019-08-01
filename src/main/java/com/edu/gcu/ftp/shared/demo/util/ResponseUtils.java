package com.edu.gcu.ftp.shared.demo.util;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtils {

  public static Map buildResponse(boolean isok,int code,Object data ){
    Map result = new HashMap();
    result.put("isok",isok);
    result.put("coed",code);
    result.put("data",data);
    return result;

  }



  public static  Map buildSuccessResponse(Object data){
    return   buildResponse(true,200,data);
  }

  public static Map buildErrorResponse(Object data){
    return   buildResponse(false,500,data);
  }


  public static Map buildNullResponse(){
    return  buildResponse(false,500,"无法获取该数据");
  }

  public static Map buildUnauth(){
    return  buildResponse(false,401,"权限错误，没有改权限");
  }



}
