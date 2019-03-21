package com.example.jtademo.controller;

import com.beust.jcommander.internal.Lists;
import com.example.jtademo.annotation.ExcelBody;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class FileController {

    @GetMapping("/exportExcel")
    @ExcelBody
    public List<String> exportExcel() {
        return Lists.newArrayList("张三", "李四");
    }
}
