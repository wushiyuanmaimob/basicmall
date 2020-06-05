package com.wushiyuan.basicmall.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.wushiyuan.basicmall.product.service.CategoryBrandRelationService;
import com.wushiyuan.basicmall.product.vo.Catelog2Vo;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wushiyuan.common.utils.PageUtils;
import com.wushiyuan.common.utils.Query;

import com.wushiyuan.basicmall.product.dao.CategoryDao;
import com.wushiyuan.basicmall.product.entity.CategoryEntity;
import com.wushiyuan.basicmall.product.service.CategoryService;

import javax.annotation.Resource;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Resource
    CategoryBrandRelationService categoryBrandRelationService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    RedissonClient redisson;

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

    @Override
    public List<Long> findCategoryPath(Long catelogId) {
        ArrayList<Long> paths = new ArrayList<>();

        List<Long> parentPath = findParentPath(catelogId, paths);

        Collections.reverse(parentPath);

        return parentPath;
    }

    //级联更新
    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        if (!StringUtils.isEmpty(category.getName())) {
            categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());
        }
    }

    @Override

                //每一个需要缓存的数据都来指定要放到那个名字的缓存【缓存分区，按照业务来区分】
                //代表当前方法的结果需要缓存，如果缓存中有，方法不用调用。如果缓存中没有，会调用用方法，最后将方法的结果放入缓存
    @Cacheable(value = {"category"}, key = "'category_level1'")
    public List<CategoryEntity> getLevel1Categorys() {
        long id = Thread.currentThread().getId();
        System.out.println("getLevel1Categorys" + id);
        List<CategoryEntity> categoryEntities = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));

        return categoryEntities;
    }

    @Override
    public Map<String, List<Catelog2Vo>> getCatalogJson() {
        //1、从 redis 中获取分类
        String catalogJson = redisTemplate.opsForValue().get("catalogJson");
        if (Strings.isEmpty(catalogJson)) {

            System.out.println("缓存不命中...将要查询数据库...");

            //2、未获取到则从 Mysql 中查询
            Map<String, List<Catelog2Vo>> catalogJsonFromDb = getCatalogJsonFromDbWithRedisLock();

            return catalogJsonFromDb;
        }

        System.out.println("缓存命中...直接返回...");

        Map<String, List<Catelog2Vo>> stringListMap = JSON.parseObject(catalogJson, new TypeReference<Map<String, List<Catelog2Vo>>>() {});

        return stringListMap;
    }

    public Map<String, List<Catelog2Vo>> getCatalogJsonFromDbWithRedisLock() {

        String token = UUID.randomUUID().toString();
        //1、分布式锁，设置key和过期时间必须是原子操作
        Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", token, 300, TimeUnit.SECONDS);
        if (lock) {
            System.out.println("获取分布式锁成功...执行业务");
            Map<String, List<Catelog2Vo>> data = null;
            try {
                data = getData();
            }finally {
                //业务执行完成之后，释放锁 必须是原子操作 lua脚本实现
                String script = "if redis.call('get',KEYS[1]) == ARGV[1]" +
                        " then " +
                        "return redis.call('del', KEYS[1])" +
                        " else" +
                        " return 0" +
                        " end";
                redisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class), Arrays.asList("lock"), token);
            }

            return data;
        } else {
            //加锁失败  重试...
            //自旋的方式
            System.out.println("获取分布式锁失败...等待重试");
            try {
                Thread.sleep(200);
            }catch (Exception e) {

            }

            return getCatalogJsonFromDbWithLocalLock();
        }
    }

    /**
     * 问题：缓存中的数据如何和数据库保存一致
     * 两种常用方式：
     *  1）、双写模式--写数据库的同时，更新缓存
     *  2）、失效模式--写数据库的同时，删除缓存
     *
     *  最终一致性
     *
     *  保证：1、缓存中的所有数据都有过期时间，数据过期下一次查询触发主动更新
     *      2、读写数据的时候，加上分布式的读写锁
     * @return
     */
    public Map<String, List<Catelog2Vo>> getCatalogJsonFromDbWithRedissonLock() {

        //1、锁的名字，锁的力度，越细越快
        //所得粒度：具体缓存的是某个数据，11-号商品 product-11-lock product-12-lock
        RLock lock = redisson.getLock("CatalogJson-lock");

        Map<String, List<Catelog2Vo>> data = null;
        try {
            lock.lock();
            data = getData();
        }finally {
            lock.unlock();
        }

        return data;

    }

    private Map<String, List<Catelog2Vo>> getData() {
        String catalogJson = redisTemplate.opsForValue().get("catalogJson");
        if (!StringUtils.isEmpty(catalogJson)) {
            Map<String, List<Catelog2Vo>> stringListMap = JSON.parseObject(catalogJson, new TypeReference<Map<String, List<Catelog2Vo>>>() {
            });

            return stringListMap;
        }

        System.out.println("查询了数据库...");

        //查出所有分类
        List<CategoryEntity> selectList = baseMapper.selectList(new QueryWrapper<CategoryEntity>());

        //查出所有1级分类
        List<CategoryEntity> level1Categorys = getParent_cid(selectList, 0l);

        Map<String, List<Catelog2Vo>> parent_id = level1Categorys.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
            //1、每一个的一级分类，继续查一级分类的二级分类
            List<CategoryEntity> categoryEntities = getParent_cid(selectList, v.getCatId());

            List<Catelog2Vo> catelog2Vos = null;
            if (Objects.nonNull(categoryEntities)) {
                catelog2Vos = categoryEntities.stream().map(l2 -> {
                    Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(), null, l2.getCatId().toString(), l2.getName());
                    List<CategoryEntity> level3Catelog = getParent_cid(selectList, l2.getCatId());
                    if (Objects.nonNull(level3Catelog)) {
                        List<Catelog2Vo.Catelog3Vo> collect = level3Catelog.stream().map(l3 -> {
                            Catelog2Vo.Catelog3Vo catelog3Vo = new Catelog2Vo.Catelog3Vo(l2.getCatId().toString(), l3.getCatId().toString(), l3.getName());
                            return catelog3Vo;
                        }).collect(Collectors.toList());
                        catelog2Vo.setCatelog3List(collect);
                    }
                    return catelog2Vo;
                }).collect(Collectors.toList());
            }

            return catelog2Vos;
        }));

        String s = JSON.toJSONString(parent_id);
        //将查询结果序列化写入 redis
        redisTemplate.opsForValue().set("catalogJson", s);

        return parent_id;
    }

    public Map<String, List<Catelog2Vo>> getCatalogJsonFromDbWithLocalLock() {

        //TODO 本地锁: synchronized JUC(lock)，在分布式情况下，想要锁住所有，必须使用分布式锁
        synchronized (this) {

            //得到锁以后，应该再从缓存中确定是否有数据
            return getData();
        }

    }

    private List<CategoryEntity> getParent_cid(List<CategoryEntity> selectList, Long parentCid) {
        return selectList.stream().filter(item -> item.getParentCid() == parentCid).collect(Collectors.toList());
    }

    public List<Long> findParentPath(Long categoryId, List<Long> paths) {
        paths.add(categoryId);
        CategoryEntity byId = this.getById(categoryId);
        if (byId.getParentCid() != 0) {
            findParentPath(byId.getParentCid(), paths);
        }

        return paths;
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