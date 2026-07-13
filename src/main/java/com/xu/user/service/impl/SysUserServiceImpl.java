package com.xu.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xu.admin.common.entity.Result;
import com.xu.user.domain.dto.LoginDTO;
import com.xu.user.entity.SysUser;
import com.xu.user.domain.vo.LoginVO;
import com.xu.user.mapper.SysUserMapper;
import com.xu.user.mapper.UserPointMapper;
import com.xu.user.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import me.ihxq.projects.pna.Attribution;
import me.ihxq.projects.pna.PhoneNumberInfo;
import me.ihxq.projects.pna.PhoneNumberLookup;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    private final UserPointMapper pointMapper;

    private final PhoneNumberLookup phoneNumberLookup;

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

        //封装vo
        LoginVO vo = BeanUtil.copyProperties(user, LoginVO.class);
        vo.setAddress(address);
        vo.setPoint(point);

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
}
