package com.example.jtademo.views;


import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxStreamingView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import static com.example.jtademo.views.XlsxView.XLSX_NAME;

@Component(XLSX_NAME)
@Order(1)
public class XlsxView extends AbstractXlsxStreamingView {

    public final static String XLSX_NAME = "xlsx";
    public final static String XLSX_VALUE = "XLSX_VALUE";
    public final static String XLSX_CLASS = "XLSX_CLASS";

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println(model);
        List entity = (List) model.get(XLSX_VALUE);
        Class classInfo = (Class) model.get(XLSX_CLASS);
        Sheet sheetAt = workbook.createSheet();
        Row row = sheetAt.createRow(0);
        row.createCell(0).setCellValue((String) entity.get(0));
        row.createCell(1).setCellValue((String) entity.get(1));
    }
}
