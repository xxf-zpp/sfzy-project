package com.xu.user.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xu.user.domain.dto.PageQueryDTO;
import com.xu.user.domain.entity.Result;
import com.xu.user.domain.entity.SysRole;
import com.xu.user.service.ISysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 系统角色表 前端控制器
 * </p>
 *
 * @author xu
 * @since 2026-07-15
 */
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class SysRoleController {

    private final ISysRoleService roleService;


    /**
     * 获取角色列表
     * @param queryDTO
     * @return
     */
    @GetMapping("/list")
    public Result<Page<SysRole>> getPage(PageQueryDTO queryDTO) {
        return roleService.getPage(queryDTO);
    }

}
