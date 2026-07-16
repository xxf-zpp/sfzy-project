package com.xu.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xu.user.domain.entity.Result;
import com.xu.user.domain.dto.PageQueryDTO;
import com.xu.user.domain.vo.ConsultVO;
import com.xu.user.domain.entity.Consult;

/**
 * <p>
 * 咨询师/心理导师主表 服务类
 * </p>
 *
 * @author xu
 * @since 2026-07-13
 */
public interface IConsultService extends IService<Consult> {

    Result<Page<ConsultVO>> getPage(PageQueryDTO queryDTO);
}
