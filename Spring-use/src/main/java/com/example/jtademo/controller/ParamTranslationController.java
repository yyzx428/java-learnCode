package com.example.jtademo.controller;

import com.example.jtademo.dto.ParamTranslationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;

@RestController
@Slf4j
public class ParamTranslationController {

    @PostMapping("/test/decoderName")
    public String getDecoderName(@RequestBody ParamTranslationDTO translationDTO) {
        log.info("ParamTranslationController入参:{}", translationDTO);
        return translationDTO.getName();
    }

    @GetMapping("/test/encoderName")
    public String getEncoderName() {
        return URLEncoder.encode("张三");
    }
}
