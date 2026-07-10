package com.xu.user.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统用户表
 * </p>
 *
 * @author author
 * @since 2026-07-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
@ApiModel(value="SysUser对象", description="系统用户表")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户编号，主键自增")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "登录账号，唯一")
    private String username;

    @ApiModelProperty(value = "密码（加密后）")
    private String userpwd;

    @ApiModelProperty(value = "手机号码，唯一")
    private String mobile;

    @ApiModelProperty(value = "邮箱，唯一")
    private String email;

    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @ApiModelProperty(value = "加密盐")
    private String salt;

    @ApiModelProperty(value = "头像图片url")
    private String avatar;

    @ApiModelProperty(value = "是否删除：0正常，1删除")
    private Boolean del;


}
