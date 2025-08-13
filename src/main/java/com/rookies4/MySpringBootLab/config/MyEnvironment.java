package com.rookies4.MySpringBootLab.config;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class MyEnvironment {
    String mode;
}
