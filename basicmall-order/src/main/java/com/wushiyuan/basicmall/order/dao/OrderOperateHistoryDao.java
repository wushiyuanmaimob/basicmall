package com.wushiyuan.basicmall.order.dao;

import com.wushiyuan.basicmall.order.entity.OrderOperateHistoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单操作历史记录
 * 
 * @author wushiyuan
 * @email wushiyuanwork@outlook.com
 * @date 2020-05-15 16:34:38
 */
@Mapper
public interface OrderOperateHistoryDao extends BaseMapper<OrderOperateHistoryEntity> {
	
}
