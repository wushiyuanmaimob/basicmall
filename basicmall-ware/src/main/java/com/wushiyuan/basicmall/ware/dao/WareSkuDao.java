package com.wushiyuan.basicmall.ware.dao;

import com.wushiyuan.basicmall.ware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品库存
 * 
 * @author wushiyuan
 * @email wushiyuanwork@outlook.com
 * @date 2020-05-15 17:06:52
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {

    Long getSkuStock(Long skuId);
}
