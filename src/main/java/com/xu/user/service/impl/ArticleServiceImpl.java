package com.xu.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xu.user.domain.entity.Result;
import com.xu.user.domain.dto.PageQueryDTO;
import com.xu.user.domain.vo.SimArticleVO;
import com.xu.user.domain.entity.Article;
import com.xu.user.mapper.ArticleMapper;
import com.xu.user.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author xu
 * @since 2026-07-11
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {


    @Override
    public Result<List<SimArticleVO>> getSimpleArticleList() {
        List<Article> list = lambdaQuery()
                .eq(Article::getDel, 0)
                .eq(Article::getStatus, 1)
                .orderByDesc(Article::getFavoriteNum)
                .last("limit 3")
                .list();
        List<SimArticleVO> listVO = new ArrayList<>(3);
        for (Article article : list) {
            SimArticleVO simArticleVO = BeanUtil.copyProperties(article, SimArticleVO.class);
            listVO.add(simArticleVO);
        }
        return Result.ok(listVO);
    }

    @Override
    public Result<Page<Article>> articlePage(PageQueryDTO queryDTO) {
        Page<Article> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        Page<Article> p = lambdaQuery()
                .eq(Article::getStatus, 1)
                .orderByDesc(Article::getFavoriteNum)
                .page(page);
        return Result.ok(p);
    }
}
