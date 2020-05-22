package com.wushiyuan.basicmall.product.service.impl;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wushiyuan.common.utils.PageUtils;
import com.wushiyuan.common.utils.Query;

import com.wushiyuan.basicmall.product.dao.CategoryDao;
import com.wushiyuan.basicmall.product.entity.CategoryEntity;
import com.wushiyuan.basicmall.product.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        //获取所有的分类
        List<CategoryEntity> entities = baseMapper.selectList(null);

        //组装成树状结构
            //1、找到所有的一级分类
        List<CategoryEntity> level1Menus = entities.stream().filter(entity ->
            entity.getParentCid() == 0
        ).map(menu -> {
            menu.setChildren(getChildren(menu, entities));
            return menu;
        }).sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort()))
        ).collect(Collectors.toList());

        return level1Menus;
    }

    @Override
    public void removeMnuByIds(List<Long> asList) {
        //TODO
        baseMapper.deleteBatchIds(asList);
    }

    //递归获取子菜单
    private List<CategoryEntity> getChildren(CategoryEntity root, List<CategoryEntity> all)
    {
        List<CategoryEntity> children = all.stream().filter(entity ->
                root.getCatId() == entity.getParentCid()
        ).map((categoryEntity) -> {
            categoryEntity.setChildren(getChildren(categoryEntity, all));
            return categoryEntity;
        }).sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort()))
        ).collect(Collectors.toList());

        return children;
    }

}