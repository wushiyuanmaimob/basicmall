package com.wushiyuan.basicmall.member.dao;

import com.wushiyuan.basicmall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author wushiyuan
 * @email wushiyuanwork@outlook.com
 * @date 2020-05-15 15:58:51
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
