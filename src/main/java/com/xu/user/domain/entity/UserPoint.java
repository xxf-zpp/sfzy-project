package com.xu.user.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 用户积分表
 * </p>
 *
 * @author author
 * @since 2026-07-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_point")
@ApiModel(value="UserPoint对象", description="用户积分表")
public class UserPoint implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自增主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "关联系统用户表sys_user.id")
    private Integer userId;

    @ApiModelProperty(value = "用户当前积分，新注册默认赠送积分")
    private Integer point;

    @ApiModelProperty(value = "逻辑删除：0正常，1已删除")
    private Integer del;


}
