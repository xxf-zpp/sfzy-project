package com.xu.user.mapper;

import com.xu.user.domain.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 系统角色表 Mapper 接口
 * </p>
 *
 * @author xu
 * @since 2026-07-15
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 根据用户id获取对应的角色id
     * @param id
     * @return
     */
    @Select("select r.role_name from sys_user u join sys_user_role ur on u.id = ur.user_id join sys_role r on ur.role_id = r.id where u.id = #{id}")
    String getRoleNameByUserId(Integer id);
}
