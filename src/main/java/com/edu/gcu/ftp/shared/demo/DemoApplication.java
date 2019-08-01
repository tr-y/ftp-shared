package com.edu.gcu.ftp.shared.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@MapperScan("com.edu.gcu.ftp.shared.demo.dao")
@SpringBootApplication
//@ComponentScan("com.edu.gcu.ftp.shared.demo.*")
@EnableAspectJAutoProxy(proxyTargetClass=true)
//@ComponentScan(basePackages = {"com.edu.gcu.ftp.shared.demo.*.*"})
public class DemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

}

