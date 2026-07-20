package com.xu.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xu.user.domain.dto.LoginDTO;
import com.xu.user.domain.dto.PageQueryDTO;
import com.xu.user.domain.dto.RegisterDTO;
import com.xu.user.domain.dto.UserDTO;
import com.xu.user.domain.entity.Result;
import com.xu.user.domain.entity.SysUser;
import com.xu.user.domain.vo.LoginVO;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author author
 * @since 2026-07-09
 */
public interface ISysUserService extends IService<SysUser> {

    Result<LoginVO> login(LoginDTO loginDTO);

    Result<?> register(RegisterDTO registerDTO);

    Result<?> getCode(RegisterDTO registerDTO);

    Result<?> getList(PageQueryDTO queryDTO);

    Result<?> addUser(UserDTO userDTO);
}
