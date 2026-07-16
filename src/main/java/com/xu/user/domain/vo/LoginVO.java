package com.xu.user.domain.vo;

import com.xu.user.domain.enmus.SysRoleEnum;
import lombok.Data;

@Data
public class LoginVO {
    private Long id;
    private String token;
    private String username;
    private String userpwd;
    private String mobile; //手机号码
    private String nickname;
    private String avatar;
    private SysRoleEnum role;
    private Integer point;
    /**
     * 用户手机号地址
     */
    private String address;
}
