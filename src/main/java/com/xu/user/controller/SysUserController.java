package com.xu.user.controller;


import com.xu.user.domain.dto.LoginDTO;
import com.xu.user.domain.dto.PageQueryDTO;
import com.xu.user.domain.dto.RegisterDTO;
import com.xu.user.domain.dto.UserDTO;
import com.xu.user.domain.entity.Result;
import com.xu.user.domain.entity.SysUser;
import com.xu.user.domain.vo.LoginVO;
import com.xu.user.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 系统用户表 前端控制器
 * </p>
 *
 * @author author
 * @since 2026-07-09
 */
@RestController(value = "userSysUserController")
@RequestMapping("/user")
@RequiredArgsConstructor
public class SysUserController {

    private final ISysUserService userService;

    /**
     * 用户登录
     *
     * @param loginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody LoginDTO loginDTO) {
        return userService.login(loginDTO);
    }

    /**
     * 退出登录
     *
     * @return
     */
    @PostMapping("/logout")
    public Result<?> logout() {
        return Result.ok("退出成功！");
    }

    /**
     * 获取手机验证码
     *
     * @return
     */
    @GetMapping("/code")
    public Result<?> getCode(RegisterDTO registerDTO) {
        return userService.getCode(registerDTO);
    }

    /**
     * 用户通过手机号注册账号
     *
     * @param registerDTO
     * @return
     */
    @PostMapping("/register")
    public Result<?> register(@RequestBody RegisterDTO registerDTO) {
        return userService.register(registerDTO);
    }

    /**
     * 用户列表分页
     *
     * @param queryDTO
     * @return
     */
    @GetMapping("/list")
    public Result<?> getList(PageQueryDTO queryDTO) {
        return userService.getList(queryDTO);
    }

    /**
     * 新增用户
     *
     * @param userDTO
     * @return
     */
    @PostMapping("/add")
    public Result<?> addUser(@RequestBody UserDTO userDTO) {
        return userService.addUser(userDTO);
    }

    /**
     * 批量删除用户
     * @param ids
     * @return
     */
    @PostMapping("/remove")
    public Result<?> removeBatch(@RequestParam("ids") List<Long> ids) {
        userService.removeBatchByIds(ids);
        return Result.ok();
    }

    /**
     * 更新用户信息
     * @param userDTO
     * @return
     */
    @PutMapping("/update")
    public Result<?> updateUserById(@RequestBody UserDTO userDTO){
        userService.lambdaUpdate()
                .set(SysUser::getUsername,userDTO.getUsername())
                .set(SysUser::getNickname,userDTO.getNickname())
                .set(SysUser::getEmail,userDTO.getEmail())
                .set(SysUser::getMobile,userDTO.getMobile())
                .set(SysUser::getAvatar,userDTO.getAvatar())
                .eq(SysUser::getId,userDTO.getId())
                .update();
        return Result.ok();
    }

}
