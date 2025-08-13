package com.rookies4.MySpringBootLab.property;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties("myprop")
public class MyPropProperties {
    private String username;
    private int port;
}
