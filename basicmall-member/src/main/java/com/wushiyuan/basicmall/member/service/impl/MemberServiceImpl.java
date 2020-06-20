package com.wushiyuan.basicmall.member.service.impl;

import com.wushiyuan.basicmall.member.dao.MemberLevelDao;
import com.wushiyuan.basicmall.member.entity.MemberLevelEntity;
import com.wushiyuan.basicmall.member.enums.MemberLevelDefaultStatusEnum;
import com.wushiyuan.basicmall.member.exception.PhoneExistException;
import com.wushiyuan.basicmall.member.exception.UserNameExistException;
import com.wushiyuan.basicmall.member.vo.UserLoginVo;
import com.wushiyuan.basicmall.member.vo.UserRegistVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wushiyuan.common.utils.PageUtils;
import com.wushiyuan.common.utils.Query;

import com.wushiyuan.basicmall.member.dao.MemberDao;
import com.wushiyuan.basicmall.member.entity.MemberEntity;
import com.wushiyuan.basicmall.member.service.MemberService;


@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    /**
     * @Info 会员登记表 DAO
     * @Author wushiyuanwork@outlook.com
     * @param null : 
     * @throws
     * @Date 2020/6/18 16:04
     * @Version
     */
    @Autowired
    MemberLevelDao memberLevelDao;

    /**
     * @Info 会员分页查询
     * @Author wushiyuanwork@outlook.com
     * @param params :
     * @return com.wushiyuan.common.utils.PageUtils
     * @throws
     * @Date 2020/6/18 16:03
     * @Version
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * @Info 用户注册
     * @Author wushiyuanwork@outlook.com
     * @param userRegistVo : 注册数据
     * @return void
     * @throws
     * @Date 2020/6/18 16:03
     * @Version
     */
    public void regist(UserRegistVo userRegistVo) {
        MemberEntity entity = new MemberEntity();
        //设置默认等级
        MemberLevelEntity memberLevelEntity = memberLevelDao.selectOne(new QueryWrapper<MemberLevelEntity>().eq("default_status", MemberLevelDefaultStatusEnum.YES.getCode()));
        if (memberLevelEntity != null) {
            entity.setLevelId(memberLevelEntity.getId());
        }

        //检查用户名是否唯一
        checkPhoneUnique(userRegistVo.getPhone());
        //检查手机号码是否唯一
        checkUserNameUnique(userRegistVo.getName());

        //设置用户名
        entity.setUsername(userRegistVo.getName());
        //设置手机号码
        entity.setMobile(userRegistVo.getPhone());

        //密码加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode(userRegistVo.getPassword());
        entity.setPassword(encode);

        this.baseMapper.insert(entity);
    }

    /**
     * @Info 会员登录
     * @Author wushiyuanwork@outlook.com
     * @param vo : 登录信息
     * @return com.wushiyuan.basicmall.member.entity.MemberEntity
     * @throws
     * @Date 2020/6/18 19:19
     * @Version
     */
    @Override
    public MemberEntity login(UserLoginVo vo) {
        MemberEntity memberEntity = this.baseMapper.selectOne(new QueryWrapper<MemberEntity>()
                .eq("username", vo.getUserId())
                .or()
                .eq("mobile", vo.getUserId())
                .or()
                .eq("email", vo.getUserId()));
        if (memberEntity == null) {
            return null;
        } else {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            boolean matches = bCryptPasswordEncoder.matches(vo.getPassword(), memberEntity.getPassword());
            if (matches) {
                return memberEntity;
            }
        }

        return null;
    }

    /**
     * @Info 检查手机号码是否唯一
     * @Author wushiyuanwork@outlook.com
     * @param phone :
     * @return void
     * @throws
     * @Date 2020/6/18 16:01
     * @Version
     */
    private void checkPhoneUnique(String phone) throws PhoneExistException{
        Integer selectCount = this.baseMapper.selectCount(new QueryWrapper<MemberEntity>()
                .select("id")
                .eq("mobile", phone));
        if (selectCount > 0) {
            throw new PhoneExistException();
        }
    }

    /**
     * @Info 检查用户名是否唯一
     * @Author wushiyuanwork@outlook.com
     * @param name :
     * @return void
     * @throws
     * @Date 2020/6/18 16:00
     * @Version
     */
    private void checkUserNameUnique(String name) throws UserNameExistException {
        Integer selectCount = this.baseMapper.selectCount(new QueryWrapper<MemberEntity>()
                .select("id")
                .eq("username", name));
        if (selectCount > 0) {
            throw new UserNameExistException();
        }
    }

}