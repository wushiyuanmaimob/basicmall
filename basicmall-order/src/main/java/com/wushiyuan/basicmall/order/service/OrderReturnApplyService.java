package com.wushiyuan.basicmall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wushiyuan.common.utils.PageUtils;
import com.wushiyuan.basicmall.order.entity.OrderReturnApplyEntity;

import java.util.Map;

/**
 * 订单退货申请
 *
 * @author wushiyuan
 * @email wushiyuanwork@outlook.com
 * @date 2020-05-15 16:34:38
 */
public interface OrderReturnApplyService extends IService<OrderReturnApplyEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

