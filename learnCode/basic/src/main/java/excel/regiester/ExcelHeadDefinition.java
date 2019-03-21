package excel.regiester;

import excel.cellhandler.CellStrategy;
import excel.cellhandler.CellStrategyFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class ExcelHeadDefinition {
    private String message;
    private Field field;
    private Integer cellType;
    private CellStrategy cellStrategy;

    private ExcelHeadDefinition() {
    }

    public ExcelHeadDefinition(ExcelHeadDefinition entity) {
        this.message = entity.message;
        this.field = entity.field;
        this.cellType = entity.cellType;
        this.cellStrategy = entity.cellStrategy;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Integer getCellType() {
        return cellType;
    }

    public void setCellType(Integer cellType) {
        this.cellType = cellType;
    }

    public CellStrategy getCellStrategy() {
        return cellStrategy;
    }

    public void setCellStrategy(CellStrategy cellStrategy) {
        this.cellStrategy = cellStrategy;
    }

    public static class Builder {
        private ExcelHeadDefinition entity;

        Builder() {
            this.entity = new ExcelHeadDefinition();
        }

        public Builder message(String message) {
            entity.message = message;
            return this;
        }

        public Builder field(Field field) {
            entity.field = field;
            return this;
        }

        public Builder cellStrategy(CellStrategy cellStrategy) {
            entity.cellStrategy = cellStrategy;
            return this;
        }

        public ExcelHeadDefinition builder() {
            if (entity.field != null) {
                ReflectionUtils.makeAccessible(entity.field);
                if (TypeMapRegister.typeMap.containsKey(entity.field.getType())) {
                    entity.cellType = TypeMapRegister.typeMap.get(entity.field.getType());
                }
            }

            if (entity.cellType == null) {
                entity.cellType = Cell.CELL_TYPE_STRING;
            }

            if (entity.cellStrategy == null && entity.field != null) {
                entity.cellStrategy = CellStrategyFactory.getCellStrategy(entity.field.getType());
            } else {
                entity.cellStrategy = CellStrategyFactory.getCellStrategy(String.class);
            }
            return new ExcelHeadDefinition(entity);
        }
    }
}
