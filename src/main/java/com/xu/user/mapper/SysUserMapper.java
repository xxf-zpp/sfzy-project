package com.xu.user.mapper;

import com.xu.user.domain.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 系统用户表 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2026-07-09
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    @Select("select count(u.id) from sys_user u join sys_user_role r on u.id = r.user_id where r.role_id = #{roleId}")
    Integer selectUserByRoleId(Long roleId);

}
