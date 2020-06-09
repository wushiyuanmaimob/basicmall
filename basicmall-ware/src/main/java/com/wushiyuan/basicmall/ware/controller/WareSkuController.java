package com.wushiyuan.basicmall.ware.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.wushiyuan.basicmall.ware.vo.SkuHasStockVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wushiyuan.basicmall.ware.entity.WareSkuEntity;
import com.wushiyuan.basicmall.ware.service.WareSkuService;
import com.wushiyuan.common.utils.PageUtils;
import com.wushiyuan.common.utils.R;



/**
 * 商品库存
 *
 * @author wushiyuan
 * @email wushiyuanwork@outlook.com
 * @date 2020-05-15 17:06:52
 */
@RestController
@RequestMapping("ware/waresku")
public class WareSkuController {
    @Autowired
    private WareSkuService wareSkuService;

    @PostMapping("/hasstock")
    public R<List<SkuHasStockVo>> getSkusHasStock(@RequestBody List<Long> skuIds) {
        List<SkuHasStockVo> vos = wareSkuService.getSkusHasStock(skuIds);

        R<List<SkuHasStockVo>> ok = R.ok();
        ok.setData(vos);

        return ok;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = wareSkuService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		WareSkuEntity wareSku = wareSkuService.getById(id);

        return R.ok().put("wareSku", wareSku);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody WareSkuEntity wareSku){
		wareSkuService.save(wareSku);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody WareSkuEntity wareSku){
		wareSkuService.updateById(wareSku);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		wareSkuService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
