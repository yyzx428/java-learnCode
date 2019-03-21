package com.example.jtademo.controller;

import com.example.jtademo.constants.GroupType;
import com.example.jtademo.dto.ParameterDto;
import com.example.jtademo.entity.Student;
import com.example.jtademo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class StudentController extends BaseController {
    @Autowired
    StudentService studentService;

    @GetMapping("/getAll")
    public List<Student> getAllStudent() {
        return studentService.queryList();
    }

    @PostMapping("/insert")
    @Validated(GroupType.Add.class)
    public Integer insert(@RequestBody @Valid ParameterDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(x -> System.out.println(x.getDefaultMessage()));
        }
        return 1;
    }

    @PostMapping("/update")
    @Validated(GroupType.Edit.class)
    public Integer update(@RequestBody @Valid ParameterDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(x -> System.out.println(x.getDefaultMessage()));
        }
        return 1;
    }
}
