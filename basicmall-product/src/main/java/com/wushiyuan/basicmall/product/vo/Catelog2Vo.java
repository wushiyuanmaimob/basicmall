package com.wushiyuan.basicmall.product.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Catelog2Vo {
    private String catelogId;   //1级父分类 id
    private List<Catelog3Vo> catelog3List;  //三级子分类
    private String id;
    private String name;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Catelog3Vo {
        private String catelog2Id;  //父分类，2级分类id
        private String id;
        private String name;
    }
}
