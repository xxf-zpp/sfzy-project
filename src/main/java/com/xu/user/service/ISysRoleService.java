package com.xu.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xu.user.domain.dto.PageQueryDTO;
import com.xu.user.domain.entity.Result;
import com.xu.user.domain.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统角色表 服务类
 * </p>
 *
 * @author xu
 * @since 2026-07-15
 */
public interface ISysRoleService extends IService<SysRole> {

    Result<Page<SysRole>> getPage(PageQueryDTO queryDTO);

    Result<?> deleteRoleByName(String roleName);

    Result<?> updateRole(SysRole role);
}
