package com.wushiyuan.basicmall.coupon.dao;

import com.wushiyuan.basicmall.coupon.entity.CouponSpuRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券与产品关联
 * 
 * @author wushiyuan
 * @email wushiyuanwork@outlook.com
 * @date 2020-05-15 10:55:02
 */
@Mapper
public interface CouponSpuRelationDao extends BaseMapper<CouponSpuRelationEntity> {
	
}
