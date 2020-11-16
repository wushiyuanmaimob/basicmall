package com.wushiyuan.com.datastructures.queue;

/**
 * @ClassName ArrayQueue
 * @Info 使用数组模拟队列
 * @Author wushiyuanwork@outlook.com
 * @Date 2020/6/25 13:15
 * @Version
 **/
//@Slf4j
public class ArrayQueue {

    //队列大小
    private int maxSize;
    //队列头前一位
    private int head;
    //队列尾
    private int tail;
    //存放队列
    private int[] queue;

    public ArrayQueue(int maxSize) {
        this.maxSize = maxSize;
        this.head = -1;
        this.tail = -1;
        this.queue = new int[maxSize];
    }

    public Boolean isFull() {
        return tail == maxSize - 1;
    }

    public Boolean isEmpty() {
        return head == tail;
    }

    public void release() {
        queue = new int[maxSize];
        tail = -1;
        head = -1;
    }

    public void addQueue(int item) {
        if (isFull()) {
            if (isEmpty())
                release();
            else
                throw new RuntimeException("队列已满...");

        }
        tail ++;
        queue[tail] = item;
    }

    public int getQueue() {
        if (isEmpty()) {
            throw new RuntimeException("队列为空，无数据...");
        }
        head ++;

        return queue[head];
    }

    public void showQueue() {
        if (isEmpty()) {
            throw new RuntimeException("队列为空");
        }
        for (int i = 0; i < queue.length; i ++) {
            System.out.printf("queue[%d]=%d\n", i, queue[i]);
        }
    }

    public int headQueue() {
        if (isEmpty()) {
            throw new RuntimeException("队列为空，无数据...");
        }

        return queue[head + 1];
    }

}
