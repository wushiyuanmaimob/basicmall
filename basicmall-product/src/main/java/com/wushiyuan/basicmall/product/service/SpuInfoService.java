package com.wushiyuan.basicmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wushiyuan.common.utils.PageUtils;
import com.wushiyuan.basicmall.product.entity.SpuInfoEntity;

import java.util.Map;

/**
 * spu信息
 *
 * @author wushiyuan
 * @email wushiyuanwork@outlook.com
 * @date 2020-05-12 10:26:45
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 商品商家
     * @param spuId
     */
    void up(Long spuId);
}

