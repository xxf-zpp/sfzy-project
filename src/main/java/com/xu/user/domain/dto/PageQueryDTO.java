package com.xu.user.domain.dto;

import lombok.Data;

@Data
public class PageQueryDTO {
    private Integer pageSize = 10;
    private Integer PageNum = 1;
    private Integer status;
}
