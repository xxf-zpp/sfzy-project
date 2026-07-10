package com.xu.user.domain.enmus;

import lombok.Getter;

/**
 * 系统角色枚举
 * 对应数据库 sys_role 表
 */
@Getter
public enum SysRoleEnum {

    ADMIN("admin", "管理员", "拥有系统全部操作权限"),
    CONSULT("consult", "咨询师", "仅咨询师相关数据管理权限"),
    TEACHER("teacher", "心理导师", "课程、文章等内容管理权限"),
    USER("user", "普通用户", "小程序用户，后台无操作权限");

    /** 角色编码（唯一标识，权限判断用） */
    private final String roleCode;
    /** 角色中文名称 */
    private final String roleName;
    /** 角色描述 */
    private final String description;

    SysRoleEnum(String roleCode, String roleName, String description) {
        this.roleCode = roleCode;
        this.roleName = roleName;
        this.description = description;
    }

    /**
     * 根据roleCode反向匹配枚举
     */
    public static SysRoleEnum getByCode(String code) {
        for (SysRoleEnum value : SysRoleEnum.values()) {
            if (value.getRoleCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}