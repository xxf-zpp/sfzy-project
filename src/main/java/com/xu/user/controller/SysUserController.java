package com.xu.user.controller;


import com.xu.user.domain.dto.LoginDTO;
import com.xu.user.domain.dto.RegisterDTO;
import com.xu.user.domain.entity.Result;
import com.xu.user.domain.vo.LoginVO;
import com.xu.user.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
     * @param loginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody LoginDTO loginDTO){
        return userService.login(loginDTO);
    }

    /**
     * 退出登录
     * @return
     */
    @PostMapping("/logout")
    public Result<?> logout() {
        return Result.ok("退出成功！");
    }

    /**
     * 获取手机验证码
     * @return
     */
    @GetMapping("/code")
    public Result<?> getCode(RegisterDTO registerDTO) {
        return userService.getCode(registerDTO);
    }

    /**
     * 用户通过手机号注册账号
     * @param registerDTO
     * @return
     */
    @PostMapping("/register")
    public Result<?> register(@RequestBody RegisterDTO registerDTO) {
        return userService.register(registerDTO);
    }

}
