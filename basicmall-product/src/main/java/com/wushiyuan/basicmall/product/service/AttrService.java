package com.wushiyuan.basicmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wushiyuan.basicmall.product.vo.AttrVo;
import com.wushiyuan.common.utils.PageUtils;
import com.wushiyuan.basicmall.product.entity.AttrEntity;

import java.util.Map;

/**
 * 商品属性
 *
 * @author wushiyuan
 * @email wushiyuanwork@outlook.com
 * @date 2020-05-12 10:26:45
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttr(AttrVo attr);
}

