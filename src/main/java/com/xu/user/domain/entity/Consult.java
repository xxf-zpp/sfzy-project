package com.xu.user.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 咨询师/心理导师主表
 * </p>
 *
 * @author xu
 * @since 2026-07-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("consult")
@ApiModel(value="Consult对象", description="咨询师/心理导师主表")
public class Consult implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "咨询师主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "咨询师/心理导师姓名")
    private String name;

    @ApiModelProperty(value = "咨询师等级ID，关联consult_level.id")
    private Integer levelId;

    @ApiModelProperty(value = "手机号（同步sys_user账号）")
    private String mobile;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "头像图片URL")
    private String avatar;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "资格证书图片URL，有则认证")
    private String certImg;

    @ApiModelProperty(value = "所属地区地址")
    private String address;

    @ApiModelProperty(value = "咨询师简介")
    private String intro;

    @ApiModelProperty(value = "职位：咨询师/心理导师")
    private String position;

    @ApiModelProperty(value = "逻辑删除：0正常，1删除")
    @TableLogic
    private Integer del;


}
