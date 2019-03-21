package excel;

import excel.annotation.ExcelHead;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class DemoEntity {

    @ExcelHead(message = "序号")
    private Integer id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ExcelHead(message = "时间")
    private LocalDateTime dateTime;

    @ExcelHead(message = "项目Id")
    private String projectId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
