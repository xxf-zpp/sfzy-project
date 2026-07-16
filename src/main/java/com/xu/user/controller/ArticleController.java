package com.xu.user.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xu.user.domain.entity.Result;
import com.xu.user.domain.dto.PageQueryDTO;
import com.xu.user.domain.vo.SimArticleVO;
import com.xu.user.domain.entity.Article;
import com.xu.user.service.IArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author xu
 * @since 2026-07-11
 */
@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {

    private final IArticleService articleService;

    /**
     * 获取文章简介列表
     * @return
     */
    @GetMapping("/simple")
    public Result<List<SimArticleVO>> getSimpleArticleList() {
        return articleService.getSimpleArticleList();
    }

    /**
     * 文章分页查询
     * @param queryDTO
     * @return
     */
    @GetMapping("/list")
    public Result<Page<Article>> page(PageQueryDTO queryDTO){
        return articleService.articlePage(queryDTO);
    }

    /**
     * 根据id获取文章详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Article> getArticleById(@PathVariable("id") Integer id){
        Article article = articleService.getById(id);
        return Result.ok(article);
    }

}
