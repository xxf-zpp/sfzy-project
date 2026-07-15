package com.xu.user.domain.dto;

import lombok.Data;

@Data
public class PageQueryDTO {
    private Integer pageSize = 10;
    private Integer PageNum = 1;
    private Integer status;

    //筛选咨询师
    private String address;
    private String goodAT;
    private String level;
    private String gender;
    private String typeName;
}
