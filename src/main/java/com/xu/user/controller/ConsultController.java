package com.xu.user.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xu.user.domain.entity.Result;
import com.xu.user.domain.dto.PageQueryDTO;
import com.xu.user.domain.vo.ConsultVO;
import com.xu.user.service.IConsultService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 咨询师/心理导师主表 前端控制器
 * </p>
 *
 * @author xu
 * @since 2026-07-13
 */
@RestController
@RequestMapping("/consult")
@RequiredArgsConstructor
public class ConsultController {

    private final IConsultService consultService;

    /**
     * 咨询师信息分页
     * @param queryDTO
     * @return
     */
    @GetMapping("/list")
    public Result<Page<ConsultVO>> getPage(PageQueryDTO queryDTO){
        return consultService.getPage(queryDTO);
    }

}
