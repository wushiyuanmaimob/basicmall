package com.wushiyuan.basicmall.basicmallsearch.controller;

import com.wushiyuan.basicmall.basicmallsearch.service.ProductSaveService;
import com.wushiyuan.common.to.es.SkuEsModel;
import com.wushiyuan.common.utils.R;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/search")
@RestController
public class ElasticSaveController {

    @Resource
    ProductSaveService productSaveService;
    public R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels) {

        productSaveService.productStatusUp(skuEsModels);

        return R.ok();
    }
}
