package com.example.jtademo.config.web;

import com.alibaba.druid.wall.Violation;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ViolationImp implements Violation {

    private String field;
    private String defaultMessage;

    @Override
    public int getErrorCode() {
        return 0;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
