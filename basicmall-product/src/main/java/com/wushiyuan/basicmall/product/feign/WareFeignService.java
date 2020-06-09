package com.wushiyuan.basicmall.product.feign;


import com.wushiyuan.common.to.SkuHasStockVo;
import com.wushiyuan.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("basicmall-ware")
public interface WareFeignService {
    @PostMapping("/ware/waresku/hasstock")
    R<List<SkuHasStockVo>> getSkusHasStock(@RequestBody List<Long> skuIds);


}
