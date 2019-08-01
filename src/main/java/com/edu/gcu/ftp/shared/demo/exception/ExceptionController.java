package com.edu.gcu.ftp.shared.demo.exception;

import com.edu.gcu.ftp.shared.demo.util.ResponseUtils;
import java.util.Map;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionController {


  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(Exception.class)
  public Map defaultExceptionHandler(HttpServletRequest request,Exception e) {
    return ResponseUtils.buildResponse(false,getStatusCode(request).value(),e.getMessage());
  }

  public HttpStatus getStatusCode(HttpServletRequest request){
    Integer code  = (Integer)request.getAttribute("javax.servlet.error.status_code");
    if(code == null){
      return HttpStatus.INTERNAL_SERVER_ERROR;
    }
   return HttpStatus.valueOf(code);
  }






}
