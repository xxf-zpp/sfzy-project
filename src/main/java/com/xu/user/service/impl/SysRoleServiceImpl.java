package com.xu.user.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xu.user.domain.dto.PageQueryDTO;
import com.xu.user.domain.entity.Result;
import com.xu.user.domain.entity.SysRole;
import com.xu.user.mapper.SysRoleMapper;
import com.xu.user.mapper.SysUserMapper;
import com.xu.user.service.ISysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    private final static String ADMIN = "admin";

    private final SysUserMapper userMapper;

    @Override
    public Result<Page<SysRole>> getPage(PageQueryDTO queryDTO) {
        Page<SysRole> page = lambdaQuery()
                .like(queryDTO.getRoleCode() != null,SysRole::getRoleCode, queryDTO.getRoleCode() + "%")
                .like(queryDTO.getRoleName() != null,SysRole::getRoleName, "%" + queryDTO.getRoleName() + "%")
                .page(new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize()));
        return Result.ok(page);
    }

    @Override
    public Result<?> deleteRoleByName(String roleName) {
        //1.查询当前的角色信息
        SysRole role = lambdaQuery().eq(SysRole::getRoleName, roleName).one();
        //2.查询对应角色的用户
        Integer userCount = userMapper.selectUserByRoleId(role.getId());
        if(userCount > 0){
            //存在对应的用户，不能删除
            return Result.fail( roleName + "存在对应的用户！");
        }
        //3.删除角色
        removeById(role.getId());
        return Result.ok();
    }

    @Override
    public Result<?> updateRole(SysRole role) {
        //1.根据id查询当前角色为修改之前的信息
        SysRole roleById = getById(role.getId());
        if(roleById.getRoleCode().equals(ADMIN)){
            return Result.fail("没有权限修改该角色！");
        }
        //2.正常修改角色信息
        boolean success = lambdaUpdate()
                .set(SysRole::getRoleName, role.getRoleName())
                .set(SysRole::getRoleCode, role.getRoleCode())
                .eq(SysRole::getId, role.getId())
                .update();
        if(!success){
            return Result.fail("系统错误");
        }
        return Result.ok();
    }
}
