package excel;

import excel.regiester.ExcelHeadDefinition;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.OutputStream;
import java.util.List;

public interface ExcelFlow {
    List<ExcelHeadDefinition> readClass();

    void generateHead();

    void write(OutputStream stream);

    <T> void writeDate(List<T> list);

    Sheet getCurrentSheet();
}
