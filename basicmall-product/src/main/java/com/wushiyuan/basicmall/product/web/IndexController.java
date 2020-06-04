package com.wushiyuan.basicmall.product.web;

import com.wushiyuan.basicmall.product.entity.CategoryEntity;
import com.wushiyuan.basicmall.product.service.CategoryService;
import com.wushiyuan.basicmall.product.vo.Catelog2Vo;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Resource
    CategoryService categoryService;

    @Autowired
    RedissonClient redissonClient;

    @GetMapping({"/", "/index.html"})
    public String indexPage(Model model) {

        //TODO查询所有的一级分类
        List<CategoryEntity> categoryEntityList = categoryService.getLevel1Categorys();
        model.addAttribute("categorys", categoryEntityList);

        return "index";
    }

    @ResponseBody
    @GetMapping("/index/catalog.json")
    public Map<String, List<Catelog2Vo>> getCatalogJson() {
        Map<String, List<Catelog2Vo>> catalogJson = categoryService.getCatalogJson();

        return catalogJson;
    }

    @ResponseBody
    @GetMapping("/hello")
    public String Hello() {
        RLock lock = redissonClient.getLock("my_lock");

        lock.lock();

        try {
            System.out.println("加锁成功，执行业务..." + Thread.currentThread().getId());
            Thread.sleep(60000);
        } catch (Exception e) {

        } finally {
            System.out.println("释放锁..." + Thread.currentThread().getId());
            lock.unlock();
        }

        return "world";
    }
}
