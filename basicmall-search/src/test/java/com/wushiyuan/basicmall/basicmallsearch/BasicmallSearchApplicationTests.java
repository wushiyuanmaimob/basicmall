package com.wushiyuan.basicmall.basicmallsearch;

import com.alibaba.fastjson.JSON;
import com.wushiyuan.basicmall.basicmallsearch.config.BasicmallElasticSearchConfig;
import lombok.Data;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class BasicmallSearchApplicationTests {

    @Autowired
    private RestHighLevelClient client;

    @Test
    void contextLoads() {
        System.out.println(client);
    }

    /**
     * 存储数据测试
     */
    @Test
    void indexData() throws IOException {
        IndexRequest request = new IndexRequest("users");
        request.id("1");
        User user = new User();
        user.setName("jack");
        user.setAge(18);
        user.setGender("F");

        String jsonString = JSON.toJSONString(user);
        request.source(jsonString, XContentType.JSON);

        //执行操作
        IndexResponse indexResponse = client.index(request, BasicmallElasticSearchConfig.COMMON_OPTIONS);

        //提取有用的响应数据
        System.out.println(indexResponse);
    }

}

@Data
class User {
    private String name;
    private Integer age;
    private String gender;
}
