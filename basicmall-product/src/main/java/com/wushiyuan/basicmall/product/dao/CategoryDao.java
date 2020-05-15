package com.wushiyuan.basicmall.product.dao;

import com.wushiyuan.basicmall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author wushiyuan
 * @email wushiyuanwork@outlook.com
 * @date 2020-05-12 10:26:45
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
