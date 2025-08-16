package com.rookies4.myspringbootlab.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorObject {
    private String message;
    private int status;
}