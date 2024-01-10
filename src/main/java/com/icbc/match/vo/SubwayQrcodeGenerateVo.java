package com.icbc.match.vo;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SubwayQrcodeGenerateVo {

    @NotBlank(message = "出发地不能为空")
    @Pattern(regexp = "^\\d{4}$", message = "站点参数不合法")
    private String startStation;

    @NotBlank(message = "目的地不能为空")
    private String endStation;

    public String getStartStation() {
        return startStation;
    }

    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    public String getEndStation() {
        return endStation;
    }

    public void setEndStation(String endStation) {
        this.endStation = endStation;
    }

}
