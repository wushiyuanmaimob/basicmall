package com.wushiyuan.basicmall.product.service.impl;

import com.wushiyuan.basicmall.product.entity.*;
import com.wushiyuan.basicmall.product.feign.WareFeignService;
import com.wushiyuan.basicmall.product.service.BrandService;
import com.wushiyuan.basicmall.product.service.CategoryService;
import com.wushiyuan.basicmall.product.service.ProductAttrValueService;
import com.wushiyuan.common.to.SkuHasStockVo;
import com.wushiyuan.common.to.es.SkuEsModel;
import com.wushiyuan.common.utils.R;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wushiyuan.common.utils.PageUtils;
import com.wushiyuan.common.utils.Query;

import com.wushiyuan.basicmall.product.dao.SpuInfoDao;
import com.wushiyuan.basicmall.product.service.SpuInfoService;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Autowired
    SkuInfoServiceImpl skuInfoService;

    @Autowired
    BrandService brandService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductAttrValueService productAttrValueService;

    @Autowired
    WareFeignService wareFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void up(Long spuId) {

        List<SkuEsModel> uoProducts = new ArrayList<>();

        //TODO 4、查询当前 sku 的所有可以被用来检索规格属性
        List<ProductAttrValueEntity> baseAttr = productAttrValueService.baseAttrlistforspu(spuId);
        List<Long> attrIds = baseAttr.stream().map(attr -> {
            return attr.getAttrId();
        }).collect(Collectors.toList());

        List<Long> searchAttrIds = productAttrValueService.selectSearchAttrIds(attrIds);

        Set<Long> idSet = new HashSet<>(searchAttrIds);

        List<SkuEsModel.Attrs> attrsList = baseAttr.stream().filter(item -> {
            return idSet.contains(item.getAttrId());
        }).map(item -> {
            SkuEsModel.Attrs attr1 = new SkuEsModel.Attrs();
            BeanUtils.copyProperties(item, attr1);

            return attr1;
        }).collect(Collectors.toList());

        //组装需要的数据
        SkuEsModel skuEsModel = new SkuEsModel();

        //1、查询 spuId 对应的所有 sku 信息，品牌的名字、
        List<SkuInfoEntity> skuInfoEntities = skuInfoService.getSkusBySpuId(spuId);
        List<Long> skuIds = skuInfoEntities.stream().map(SkuInfoEntity::getSkuId).collect(Collectors.toList());

        //TODO 1、发送远程调用，库存系统查询是否有库存
        Map<Long, Boolean> stockMap = null;
        try {
            R<List<SkuHasStockVo>> skusHasStock = wareFeignService.getSkusHasStock(skuIds);
            stockMap = skusHasStock.getData().stream().collect(Collectors.toMap(SkuHasStockVo::getSkuId, item -> item.getHasStock()));
        } catch (Exception e) {
            log.error("库存服务查询异常：原因{}", e);
        }

        //2、封装每个 sku 的信息
        Map<Long, Boolean> finalStockMap = stockMap;
        skuInfoEntities.stream().map(sku -> {
            SkuEsModel esModel = new SkuEsModel();
            BeanUtils.copyProperties(sku, esModel);

            esModel.setSkuPrice(sku.getPrice());
            esModel.setSkuImg(sku.getSkuDefaultImg());

            //设置库存信息
            if (null == finalStockMap) {
                esModel.setHasStock(true);
            } else {
                esModel.setHasStock(finalStockMap.get(sku.getSkuId()));
            }

            //TODO 2、热度评分 0
            esModel.setHotScore(0L);
            //TODO 3、查询品牌和分类的名字和信息
            BrandEntity brand = brandService.getById(esModel.getBrandId());
            esModel.setBrandName(brand.getName());
            esModel.setBrandImg(brand.getLogo());

            CategoryEntity category = categoryService.getById(esModel.getCatalogId());
            esModel.setCatalogName(category.getName());

            //设置检索属性
            esModel.setAttrs(attrsList);

            return esModel;
        }).collect(Collectors.toList());

        //TODO 5、将数据发送给 es 进行保存 basicmall-search

    }

}