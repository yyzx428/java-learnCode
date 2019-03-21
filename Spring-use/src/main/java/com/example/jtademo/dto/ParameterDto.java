package com.example.jtademo.dto;

import com.example.jtademo.constants.GroupType;
import com.example.jtademo.validProcess.CheckTimeRang;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@CheckTimeRang(startTime = "startTime", endTime = "endTime", message = "起始时间不能大于开始时间")
public class ParameterDto {

    @NotNull(groups = {GroupType.Edit.class},message = "Id不能为空")
    private String id;

    @NotNull(groups = {GroupType.Add.class, GroupType.Edit.class},message = "钱不能为空")
    private BigDecimal money;

    private Date startTime;

    private Date endTime;

    @Max(value = 200)
    private String remark;
}
