package com.wushiyuan.basicmall.ware.service.impl;

import com.wushiyuan.basicmall.ware.vo.SkuHasStockVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wushiyuan.common.utils.PageUtils;
import com.wushiyuan.common.utils.Query;

import com.wushiyuan.basicmall.ware.dao.WareSkuDao;
import com.wushiyuan.basicmall.ware.entity.WareSkuEntity;
import com.wushiyuan.basicmall.ware.service.WareSkuService;


@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),
                new QueryWrapper<WareSkuEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<SkuHasStockVo> getSkusHasStock(List<Long> skuIds) {
        List<SkuHasStockVo> collect = skuIds.stream().map(skuId -> {
            SkuHasStockVo vo = new SkuHasStockVo();

            //查询当前 sku 的总库存量
            Long count = baseMapper.getSkuStock(skuId);
            vo.setSkuId(skuId);
            vo.setHasStock(count > 0);
            return vo;
        }).collect(Collectors.toList());

        return collect;
    }

}