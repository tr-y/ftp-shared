package com.edu.gcu.ftp.shared.demo.aspect;

import com.edu.gcu.ftp.shared.demo.entity.FileUser;
import java.util.List;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class FileAspect {


  @Autowired
  private RedisTemplate redisTemplate;

  private final String USER_FILE_KEY = "user_file";


  @Pointcut(value = "within(com.edu.gcu.ftp.shared.demo.service.impl.FileUserServiceImpl)")
  public void fileAccess() {

  }


  @Around("fileAccess()")
  public Object around(ProceedingJoinPoint pjp) {
    String methodName = pjp.getSignature().getName();
    Object[] objects = pjp.getArgs();
    ZSetOperations zSetOperations = redisTemplate.opsForZSet();
    if ("insertFileUser".equals(methodName)) {
      try {
        pjp.proceed();
        FileUser fileUser = (FileUser) objects[0];
        fileUser.setId(fileUser.getId());
        zSetOperations
            .add(USER_FILE_KEY + fileUser.getUser_id() + "/" + fileUser.getParent(), fileUser,
                fileUser.getId());
        return fileUser.getId();
      } catch (Throwable throwable) {
        throwable.printStackTrace();
        return null;
      }
    } else if ("findListById".equals(methodName)) {
      int userId = (int) objects[0];
      int parentId = (int) objects[1];
      String key = USER_FILE_KEY + userId + "/" + parentId;
      try {
        if (redisTemplate.hasKey(key)) {
          long number = zSetOperations.zCard(key);
          if (number > 0) {
            return zSetOperations.range(key, 0, number);
          }
        } else {
          List<FileUser> resultList = (List) pjp.proceed();
          resultList.forEach(p -> zSetOperations.add(key, p, p.getId()));
          return resultList;
        }
      } catch (Exception e) {
        try {
          return pjp.proceed();
        } catch (Throwable throwable) {
          throwable.printStackTrace();
        }
      } catch (Throwable throwable) {
        throwable.printStackTrace();
        return null;
      }
    } else if ("delete".equals(methodName)) {
      int id = (int) objects[0];
      int userId = (int) objects[1];
      int parent = (int) objects[2];
      zSetOperations.removeRangeByScore(USER_FILE_KEY + userId + "/" + parent, id, id);
    } else if ("update".equals(methodName)) {
      FileUser fileUser = (FileUser) objects[0];
      zSetOperations
          .add(USER_FILE_KEY + fileUser.getUser_id() + "/" + fileUser.getParent(), fileUser,
              fileUser.getId());
      return null;
    }
    try {
      return pjp.proceed();
    } catch (Throwable throwable) {
      throwable.printStackTrace();
      return null;
    }
  }


}
