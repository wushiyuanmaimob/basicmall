package com.wushiyuan.basicmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wushiyuan.common.utils.PageUtils;
import com.wushiyuan.basicmall.product.entity.SkuSaleAttrValueEntity;

import java.util.Map;

/**
 * sku销售属性&值
 *
 * @author wushiyuan
 * @email wushiyuanwork@outlook.com
 * @date 2020-05-12 10:26:45
 */
public interface SkuSaleAttrValueService extends IService<SkuSaleAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

