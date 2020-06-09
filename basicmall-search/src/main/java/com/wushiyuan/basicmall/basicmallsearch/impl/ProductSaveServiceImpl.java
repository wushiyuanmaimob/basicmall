package com.wushiyuan.basicmall.basicmallsearch.impl;

import com.wushiyuan.basicmall.basicmallsearch.service.ProductSaveService;
import com.wushiyuan.common.to.es.SkuEsModel;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductSaveServiceImpl implements ProductSaveService {

    @Resource
    RestHighLevelClient restHighLevelClient;

    @Override
    public void productStatusUp(List<SkuEsModel> skuEsModels) {
        //保存到 es
        //1、给 es 中建立索引，建立好映射关系

        //2、给 es 中保存数据

    }
}
