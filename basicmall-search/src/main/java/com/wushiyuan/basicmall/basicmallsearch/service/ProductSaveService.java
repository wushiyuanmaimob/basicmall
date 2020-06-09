package com.wushiyuan.basicmall.basicmallsearch.service;

import com.wushiyuan.common.to.es.SkuEsModel;

import java.util.List;

public interface ProductSaveService {
    void productStatusUp(List<SkuEsModel> skuEsModels);
}
