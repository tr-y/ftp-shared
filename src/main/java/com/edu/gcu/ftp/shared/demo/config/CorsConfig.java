package com.edu.gcu.ftp.shared.demo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/*
  @CrosConfig 设置跨域

 */
//@Configuration
public class CorsConfig {

  private CorsConfiguration buildConfig() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.addAllowedHeader("*");//允许任何头
    corsConfiguration.addAllowedOrigin("*");//允许任何域
    corsConfiguration.addAllowedMethod("*");//允许任何方法
    return corsConfiguration;
  }


  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", buildConfig());
    return new CorsFilter(source);

  }

}





