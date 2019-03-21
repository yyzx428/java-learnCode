package com.example.jtademo.requestBodyAdvice;

import com.example.jtademo.dto.ParamTranslationDTO;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;
import java.net.URLDecoder;

public class URLDecoderRequestBodyAdvice extends RequestBodyAdviceAdapter {

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return ParamTranslationDTO.class.getName().equalsIgnoreCase(targetType.getTypeName());
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        ParamTranslationDTO entity = (ParamTranslationDTO) body;
        entity.setName(URLDecoder.decode(entity.getName()));
        return super.afterBodyRead(entity, inputMessage, parameter, targetType, converterType);
    }
}
