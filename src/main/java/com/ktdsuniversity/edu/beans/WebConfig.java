package com.ktdsuniversity.edu.beans;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configurable // Spring Boot 설정을 해주는 클래스로 변경하겠다.
@EnableWebMvc // WebMVC Module을 사용하겠다. (Validator 사용하기 위해서)
public class WebConfig implements WebMvcConfigurer {

}
