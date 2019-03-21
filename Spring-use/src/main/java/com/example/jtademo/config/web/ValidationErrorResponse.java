package com.example.jtademo.config.web;

import com.alibaba.druid.wall.Violation;

import java.util.LinkedList;
import java.util.List;

public class ValidationErrorResponse {
    private List<Violation> violations;


    public List<Violation> getViolations() {
        if (null == violations) {
            violations = new LinkedList<>();
        }
        return violations;
    }
}
