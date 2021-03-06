package com.wushiyuan.basicmall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wushiyuan.common.utils.PageUtils;
import com.wushiyuan.basicmall.coupon.entity.SeckillSkuRelationEntity;

import java.util.Map;

/**
 * 秒杀活动商品关联
 *
 * @author wushiyuan
 * @email wushiyuanwork@outlook.com
 * @date 2020-05-15 10:55:02
 */
public interface SeckillSkuRelationService extends IService<SeckillSkuRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

