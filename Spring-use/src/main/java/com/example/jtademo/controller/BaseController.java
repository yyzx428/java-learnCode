package com.example.jtademo.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;

@Validated
public class BaseController {

    @InitBinder
    protected void initBinder(final WebDataBinder binder, HttpServletRequest request) {
        System.out.println("初始化initBinder");
        System.out.println(request);
    }
}
