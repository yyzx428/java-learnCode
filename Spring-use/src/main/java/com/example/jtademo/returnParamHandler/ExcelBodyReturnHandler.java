package com.example.jtademo.returnParamHandler;

import com.example.jtademo.annotation.ExcelBody;
import com.example.jtademo.views.XlsxView;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.example.jtademo.views.XlsxView.XLSX_NAME;


public class ExcelBodyReturnHandler implements HandlerMethodReturnValueHandler {
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return returnType.hasMethodAnnotation(ExcelBody.class);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        mavContainer.getModel().addAttribute(XlsxView.XLSX_VALUE, returnValue);
        mavContainer.getModel().addAttribute(XlsxView.XLSX_CLASS, returnType.getParameterType());
        mavContainer.setView(XLSX_NAME);
    }
}
