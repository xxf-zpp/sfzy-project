package com.xu.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xu.admin.common.entity.Result;
import com.xu.user.domain.dto.PageQueryDTO;
import com.xu.user.domain.vo.ConsultVO;
import com.xu.user.entity.Consult;
import com.xu.user.entity.ConsultLevel;
import com.xu.user.mapper.ConsultMapper;
import com.xu.user.service.IConsultLevelService;
import com.xu.user.service.IConsultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 咨询师/心理导师主表 服务实现类
 * </p>
 *
 * @author xu
 * @since 2026-07-13
 */
@Service
@RequiredArgsConstructor
public class ConsultServiceImpl extends ServiceImpl<ConsultMapper, Consult> implements IConsultService {

    private final IConsultLevelService consultLevelService;

    @Override
    public Result<Page<ConsultVO>> getPage(PageQueryDTO queryDTO) {
        //1.获取咨询师信息分页
        Page<Consult> page = lambdaQuery().page(new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize()));
        List<Consult> records = page.getRecords();
        if(records == null){
            return Result.ok(new Page<>());
        }
        List<ConsultVO> voList = new ArrayList<>(records.toArray().length);
        //3.封装
        for (Consult record : records) {
            //属性拷贝
            ConsultVO consultVO = BeanUtil.copyProperties(record, ConsultVO.class);
            //查询咨询师等级
            ConsultLevel level = consultLevelService
                    .lambdaQuery()
                    .eq(ConsultLevel::getId, record.getLevelId())
                    .one();
            consultVO.setLevelName(level.getLevelName());
            voList.add(consultVO);
        }
        Page<ConsultVO> consultVOPage = new Page<>();
        consultVOPage.setRecords(voList);
        consultVOPage.setTotal(page.getTotal());
        return Result.ok(consultVOPage);
    }
}
