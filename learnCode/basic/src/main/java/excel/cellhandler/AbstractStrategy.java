package excel.cellhandler;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Workbook;

public abstract class AbstractStrategy<V> implements CellStrategy<V> {

    CellStyle style;
    DataFormat dataFormat;

    public void createCellStyle(Workbook workbook) {
        this.style = workbook.createCellStyle();
        this.dataFormat = workbook.createDataFormat();
    }

}
