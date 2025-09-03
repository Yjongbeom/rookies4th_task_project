package com.rookies4.myspringboot;

import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//Application 클래스가 Configuration(설정) 클래스 역할을 한다. @SpringBootConfiguration
//반드시 해당 클래스를 작성할때 base-package 아래에 작성을 해야 한다. @ComponentScan
//외부라이브러리에 대한 설정기능을 제공하는 AutoConfiguration을 활성화 해주는 역할. @EnableAutoConfiguration
public class MySpringBoot3ProjectApplication {

	public static void main(String[] args) {

        //SpringApplicationv.run(MySpringBoot3ProjectApplication.class, args);
        SpringApplication application = new SpringApplication(MySpringBoot3ProjectApplication.class);
        //Application 타입을 변경하기
        application.setWebApplicationType(WebApplicationType.SERVLET);
        application.run(args);
    }

    @Bean
    public String hello() {
        return "Hello SpringBoot";
    }

    @Bean
    Hibernate6Module hibernate6Module() {
        Hibernate6Module hibernate6Module = new Hibernate6Module();
        hibernate6Module.configure(Hibernate6Module.Feature.FORCE_LAZY_LOADING,true);
        return hibernate6Module;
    }

}
