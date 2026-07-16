package com.xu.user.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xu.user.domain.dto.PageQueryDTO;
import com.xu.user.domain.entity.Result;
import com.xu.user.domain.entity.SysRole;
import com.xu.user.service.ISysRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
@Slf4j
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

    /**
     * 新增角色
     * @param role
     * @return
     */
    @PostMapping("add")
    public Result<?> addRole(@RequestBody SysRole role) {
        roleService.save(role);
        return Result.ok();
    }

    /**
     * 根据角色名称删除没有对应用户的角色
     * @return
     */
    @DeleteMapping
    public Result<?> deleteRoleByName(String roleName) {
        return roleService.deleteRoleByName(roleName);
    }


    /**
     * 跟新角色信息
     * @param role
     * @return
     */
    @PostMapping("/update")
    public Result<?> updateRole(@RequestBody SysRole role){
        log.info("更新角色………………");
        return roleService.updateRole(role);
    }

}
