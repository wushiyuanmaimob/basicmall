package com.wushiyuan.basicmall.auth;

import com.wushiyuan.basicmall.auth.to.MemberEntity;
import com.wushiyuan.common.to.member.MemberInfoTo;
import com.wushiyuan.common.utils.R;
import com.wushiyuan.common.utils.Res;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class BasicmallAuthServerApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void RTest() {
        R ok = R.ok();
        ok.setData(new MemberEntity());
        log.info("#################################" + ok.getData().toString());
    }

    @Test
    void ResTest() {
        Res ok = Res.ok();
        ok.setData(new MemberInfoTo());
        log.info("##################################" + ok.toString());
    }
}
