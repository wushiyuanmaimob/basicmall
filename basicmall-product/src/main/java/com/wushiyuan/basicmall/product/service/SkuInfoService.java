package com.wushiyuan.basicmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wushiyuan.common.utils.PageUtils;
import com.wushiyuan.basicmall.product.entity.SkuInfoEntity;

import java.util.Map;

/**
 * sku信息
 *
 * @author wushiyuan
 * @email wushiyuanwork@outlook.com
 * @date 2020-05-12 10:26:45
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

