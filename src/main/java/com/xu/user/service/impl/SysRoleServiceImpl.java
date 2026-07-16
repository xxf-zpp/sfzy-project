package com.xu.user.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xu.user.domain.dto.PageQueryDTO;
import com.xu.user.domain.entity.Result;
import com.xu.user.domain.entity.SysRole;
import com.xu.user.mapper.SysRoleMapper;
import com.xu.user.service.ISysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统角色表 服务实现类
 * </p>
 *
 * @author xu
 * @since 2026-07-15
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {


    @Override
    public Result<Page<SysRole>> getPage(PageQueryDTO queryDTO) {
        Page<SysRole> page = lambdaQuery()
                .like(queryDTO.getRoleCode() != null,SysRole::getRoleCode, queryDTO.getRoleCode() + "%")
                .like(queryDTO.getRoleName() != null,SysRole::getRoleName, "%" + queryDTO.getRoleName() + "%")
                .page(new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize()));
        return Result.ok(page);
    }
}
