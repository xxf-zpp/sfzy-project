package com.xu.user.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 文章表
 * </p>
 *
 * @author xu
 * @since 2026-07-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("article")
@ApiModel(value="Article对象", description="文章表")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "文章名")
    private String articleName;

    @ApiModelProperty(value = "文章简介")
    private String intro;

    @ApiModelProperty(value = "心理导师id")
    private Integer counselorId;

    @ApiModelProperty(value = "缩略图url")
    private String thumbnail;

    @ApiModelProperty(value = "详情富文本内容")
    private String content;

    @ApiModelProperty(value = "阅读量 默认0")
    private Integer readNum;

    @ApiModelProperty(value = "所属文章分类id")
    private Integer classifyId;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "0审核中 1已审核 2未通过审核")
    private Integer status;

    @ApiModelProperty(value = "逻辑删除 0正常 1删除")
    private Integer del;

    @ApiModelProperty(value = "收藏量")
    private String favoriteNum;

    @ApiModelProperty(value = "审核备注")
    private String auditRemark;

    @ApiModelProperty(value = "是否删除 0-未删除 1-已删除")
    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty(value = "删除时间")
    private LocalDateTime deletedTime;


}
