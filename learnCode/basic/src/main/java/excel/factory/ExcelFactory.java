package excel.factory;


import excel.DefaultExcelFlow;
import excel.DemoEntity;
import excel.ExcelFlow;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class ExcelFactory {

    public static void main(String[] args) throws FileNotFoundException {
        List<DemoEntity> list = new LinkedList<>();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            list.add(initTest(i + 1));
        }
        FileOutputStream stream = new FileOutputStream(new File("C:\\Users\\29410\\Desktop\\1.xlsx"));
        ExcelFactory.generateExcel(DemoEntity.class, list, stream);
        System.out.println(System.currentTimeMillis() - startTime);
    }

    private static DemoEntity initTest(int i) {
        DemoEntity entity = new DemoEntity();
        entity.setId(i);
        entity.setDateTime(LocalDateTime.now());
        entity.setProjectId("project:" + i);
        return entity;
    }

    public static <T> void generateExcel(Class<?> target, List<T> list, OutputStream stream) {
        ExcelFlow flow = new DefaultExcelFlow(target);
        flow.readClass();
        flow.generateHead();
        flow.writeDate(list);
        flow.write(stream);
    }
}
