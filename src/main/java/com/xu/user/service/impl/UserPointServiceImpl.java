package com.xu.user.service.impl;

import com.xu.user.domain.entity.UserPoint;
import com.xu.user.mapper.UserPointMapper;
import com.xu.user.service.IUserPointService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户积分表 服务实现类
 * </p>
 *
 * @author author
 * @since 2026-07-09
 */
@Service
public class UserPointServiceImpl extends ServiceImpl<UserPointMapper, UserPoint> implements IUserPointService {

}
