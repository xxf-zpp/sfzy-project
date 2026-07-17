package com.xu.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xu.user.domain.dto.LoginDTO;
import com.xu.user.domain.dto.RegisterDTO;
import com.xu.user.domain.entity.Result;
import com.xu.user.domain.entity.SysUser;
import com.xu.user.domain.vo.LoginVO;
import com.xu.user.mapper.SysRoleMapper;
import com.xu.user.mapper.SysUserMapper;
import com.xu.user.mapper.UserPointMapper;
import com.xu.user.service.ISysUserService;
import com.xu.user.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.ihxq.projects.pna.Attribution;
import me.ihxq.projects.pna.PhoneNumberInfo;
import me.ihxq.projects.pna.PhoneNumberLookup;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static com.xu.user.content.UserContext.*;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author author
 * @since 2026-07-09
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    private final UserPointMapper pointMapper;

    private final SysRoleMapper roleMapper;

    private final PhoneNumberLookup phoneNumberLookup;

    private final JwtUtil jwtUtil;

    private final StringRedisTemplate redisTemplate;

    @Override
    public Result<LoginVO> login(LoginDTO loginDTO) {
        //1.校验账号和密码是否正确
        String username = loginDTO.getUsername();
        String userpwd = loginDTO.getUserpwd();
        SysUser user = lambdaQuery().eq(SysUser::getUsername, username).one();
        if (user == null){
            //用户账号不存在
            return Result.fail("账号或密码错误");
        }
        //账号存在，校验密码
        String userpwdMd5 = DigestUtil.md5Hex(userpwd);
        if(!userpwdMd5.equals(user.getUserpwd())){
            //密码错误
            return Result.fail("账号或密码错误");
        }
        //2.查询积分信息
        Integer point = pointMapper.getPointByUserId(user.getId());
        //3.根据手机号获取用户地址信息
        Map<String, String> addressInfo = parsePhone(user.getMobile());
        String address = addressInfo == null ? "河南-郑州" : addressInfo.get("province") + "-" + addressInfo.get("city");
        //4.生成jwt令牌
        Map<String, Object> claim = new HashMap<>();
        claim.put("username", user.getUsername());
        String token = jwtUtil.createToken(Long.valueOf(user.getId()), claim);
        //5.获取用户的角色名称
        String roleName = roleMapper.getRoleNameByUserId(user.getId());
        //6.封装vo
        LoginVO vo = BeanUtil.copyProperties(user, LoginVO.class);
        vo.setAddress(address);
        vo.setPoint(point);
        vo.setRoleName(roleName);
        vo.setToken(token);

        return Result.ok(vo);
    }

    //根据手机号推测用户地址
    private Map<String, String> parsePhone(String phone) {
        // 正确方法 lookup
        Optional<PhoneNumberInfo> infoOpt = phoneNumberLookup.lookup(phone);
        if (infoOpt.isEmpty()) {
            return null;
        }
        PhoneNumberInfo info = infoOpt.get();
        Attribution attr = info.getAttribution();

        Map<String, String> map = new HashMap<>();
        map.put("province", attr.getProvince());
        map.put("city", attr.getCity());
        return map;
    }

    @Override
    @Transactional
    public Result<?> register(RegisterDTO registerDTO) {
        String mobile = registerDTO.getMobile();
        String inpCode = registerDTO.getCode();
        String userpwd = registerDTO.getUserpwd();
        //1.判断当前手机号是否已经存在账号
        Long userCount = lambdaQuery().eq(SysUser::getMobile, mobile).count();
        if(userCount > 0){
            return Result.fail("账号已经存在，请登录！");
        }
        //2.校验验证码是否正确
        String key = CODE_KEY + mobile;
        String reallyCode = redisTemplate.opsForValue().get(key);
        if(reallyCode == null || reallyCode.isEmpty() || !reallyCode.equals(inpCode)){
            return Result.fail("验证码错误！");
        }
        //3.组装用户实体
        SysUser user = new SysUser();
        user.setUsername(DEF_USERNAME_PREV + mobile);
        // todo 这使用全局唯一id生成昵称，防止重复


        return null;
    }

    @Override
    public Result<?> getCode(RegisterDTO registerDTO) {

        /**
         * todo 这里暂时使用随机6位数作为短信验证码
         */

        //1.生成验证码
        Random random = new Random();
        Integer num = random.nextInt(900000) + 100000;
        String code = String.valueOf(num);
        //2.将验证码存入redis中
        String key = CODE_KEY + registerDTO.getMobile();
        redisTemplate.opsForValue().set(key,code,CODE_TTL, TimeUnit.MINUTES);

        //3.模拟发送验证码
        log.info("您的验证码为：{}",code);

        return Result.ok("发送成功！");
    }

    /**
     * 生成指定位数随机字符串（大小写字母+数字）
     */
    public String randomStr(int length) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int idx = ThreadLocalRandom.current().nextInt(chars.length());
            sb.append(chars.charAt(idx));
        }
        return sb.toString();
    }
}
