package com.xu.user.domain.vo;

import com.xu.user.domain.entity.SysUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserVO extends SysUser {
    @ApiModelProperty(value = "登录账号，唯一")
    private String roleName;
}
