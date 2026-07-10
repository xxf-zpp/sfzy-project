package com.xu.user.mapper;

import com.xu.user.entity.UserPoint;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 用户积分表 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2026-07-09
 */
public interface UserPointMapper extends BaseMapper<UserPoint> {

    @Select("select point from user_point where user_id = #{id}")
    Integer getPointByUserId(Integer id);
}
