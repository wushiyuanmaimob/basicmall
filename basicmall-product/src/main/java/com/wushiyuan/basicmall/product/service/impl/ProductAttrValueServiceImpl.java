package com.wushiyuan.basicmall.product.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wushiyuan.common.utils.PageUtils;
import com.wushiyuan.common.utils.Query;

import com.wushiyuan.basicmall.product.dao.ProductAttrValueDao;
import com.wushiyuan.basicmall.product.entity.ProductAttrValueEntity;
import com.wushiyuan.basicmall.product.service.ProductAttrValueService;


@Service("productAttrValueService")
public class ProductAttrValueServiceImpl extends ServiceImpl<ProductAttrValueDao, ProductAttrValueEntity> implements ProductAttrValueService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductAttrValueEntity> page = this.page(
                new Query<ProductAttrValueEntity>().getPage(params),
                new QueryWrapper<ProductAttrValueEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<ProductAttrValueEntity> baseAttrlistforspu(Long spuId) {
        List<ProductAttrValueEntity> productAttrValueEntities = this.list(new QueryWrapper<ProductAttrValueEntity>().eq("spu_id", spuId));

        return productAttrValueEntities;
    }

    @Override
    public List<Long> selectSearchAttrIds(List<Long> attrIds) {
        List<Long> searchAttrIds = this.baseMapper.selectSearchAttrIds(attrIds);

        return searchAttrIds;
    }

}