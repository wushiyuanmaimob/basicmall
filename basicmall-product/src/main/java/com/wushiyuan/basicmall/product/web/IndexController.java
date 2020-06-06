package com.wushiyuan.basicmall.product.web;

import com.wushiyuan.basicmall.product.entity.CategoryEntity;
import com.wushiyuan.basicmall.product.service.CategoryService;
import com.wushiyuan.basicmall.product.vo.Catelog2Vo;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class IndexController {

    @Resource
    CategoryService categoryService;

    @Resource
    RedissonClient redissonClient;

    @Autowired
    StringRedisTemplate redisTemplate;

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

    //可重入锁演示
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

    //读写锁演示
    //保证一定能读到最新数据，修改期间，写锁是一个排它锁（互斥锁）。读锁是一个共享锁
    //写锁没释放，读就必须等待

    //读 + 读：相当于无锁

    //写 + 读：等待写锁释放
    //写 + 写：阻塞方式
    //读 + 写：有读锁，写也需要等待

    //只要有写的存在，都必须等待
    @ResponseBody
    @GetMapping("/write")
    public String write() {
        RReadWriteLock rwLock = redissonClient.getReadWriteLock("my-read-write-lock");
        RLock lock = rwLock.writeLock();
        String s = "";
        try {
            //1、写数据加写锁
            lock.lock();
            System.out.println("写锁加锁成功" + Thread.currentThread().getId());
            s = UUID.randomUUID().toString();
            Thread.sleep(30000);
            redisTemplate.opsForValue().set("writeValue", s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println("写锁释放成功" + Thread.currentThread().getId());
        }

        return s;
    }

    @ResponseBody
    @GetMapping("/read")
    public String read() {
        RReadWriteLock rwLock = redissonClient.getReadWriteLock("my-read-write-lock");
        RLock lock = rwLock.readLock();
        String s = "";
        try {
            //2、读数据加读锁
            lock.lock();
            System.out.println("读锁加锁成功" + Thread.currentThread().getId());
            s = redisTemplate.opsForValue().get("writeValue");
            Thread.sleep(30000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println("读锁释放成功" + Thread.currentThread().getId());
        }

        return s;
    }

    //闭锁演示
    /**
     * 放假，锁门
     * 5个班全部走完，才可以锁大门
     * N 个线程全部执行完成，再执行其他业务逻辑
     */
    @ResponseBody
    @GetMapping("/lockDoor")
    public String lockDoor() throws InterruptedException {
        RCountDownLatch door = redissonClient.getCountDownLatch("door");
        door.trySetCount(5);
        door.await();
        return "放假了...";
    }

    @ResponseBody
    @GetMapping("/gogo/{id}")
    public String gogogo(@PathVariable("id") Long id) {

        RCountDownLatch door = redissonClient.getCountDownLatch("door");
        door.countDown();
        return id + "班的人都走了...";
    }

    //信号量演示
    /**
     * 车库停车
     * 3个车位
     *
     * 信号量也可以用作分布式限流
     */
    @ResponseBody
    @GetMapping("/park")
    public String park() throws InterruptedException {
        RSemaphore semaphore = redissonClient.getSemaphore("park");
//        semaphore.acquire(1); 阻塞方式
        boolean b = semaphore.tryAcquire(); //非阻塞方式

        return "park =>" + b;
    }

    @ResponseBody
    @GetMapping("/go")
    public String go() {
        RSemaphore semaphore = redissonClient.getSemaphore("park");
        semaphore.release(1);

        return "go";
    }
}
