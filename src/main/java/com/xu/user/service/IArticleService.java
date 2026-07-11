package com.xu.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xu.admin.common.entity.Result;
import com.xu.user.domain.dto.PageQueryDTO;
import com.xu.user.domain.vo.SimArticleVO;
import com.xu.user.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 文章表 服务类
 * </p>
 *
 * @author xu
 * @since 2026-07-11
 */
public interface IArticleService extends IService<Article> {

    Result<List<SimArticleVO>> getSimpleArticleList();

    Result<Page<Article>> articlePage(PageQueryDTO queryDTO);
}
