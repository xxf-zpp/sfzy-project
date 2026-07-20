package com.xu.user.domain.dto;

import lombok.Data;

@Data
public class RegisterDTO {
    private String mobile;
    private String code;
    private String userpwd;
    private String reuserpwd;
    private String email;
}
