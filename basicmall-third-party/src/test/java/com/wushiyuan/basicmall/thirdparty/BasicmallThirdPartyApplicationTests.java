package com.wushiyuan.basicmall.thirdparty;

import com.aliyun.oss.OSSClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@SpringBootTest
class BasicmallThirdPartyApplicationTests {

    @Autowired
    OSSClient ossClient;

    @Test
    void contextLoads() {
        System.out.println("np");
    }

    @Test
    public void testUpload() throws FileNotFoundException {
        InputStream inputStream = new FileInputStream("C:\\Users\\YZYX\\Pictures\\yuanbaba.png");
        ossClient.putObject("basicmall-hello", "yuanbaba.png", inputStream);

        ossClient.shutdown();

        System.out.println("上传完成...");
    }

}
