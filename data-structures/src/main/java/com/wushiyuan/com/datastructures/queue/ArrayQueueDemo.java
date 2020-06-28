package com.wushiyuan.com.datastructures.queue;

import java.util.Scanner;

/**
 * @ClassName ArrayQueueDemo
 * @Info
 * @Author wushiyuanwork@outlook.com
 * @Date 2020/6/25 13:58
 * @Version
 **/
public class ArrayQueueDemo {
    public static void main(String[] args) {
        ArrayQueue arrayQueue = new ArrayQueue(3);

        Boolean loop = true;
        char key = ' ';
        Scanner scanner = new Scanner(System.in);

        while (loop) {
            System.out.printf("s(show)：显示队列");
            System.out.printf("e(exit)：退出程序");
            System.out.printf("a(add)：添加数据到队列");
            System.out.printf("g(get)：从队列取出数据");
            System.out.printf("h(head)：查看队列头的数据");
            key = scanner.next().charAt(0);
            switch (key) {
                case 's':
                    try {
                        arrayQueue.showQueue();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'a':
                    System.out.println("请输入一个数");
                    int value = scanner.nextInt();
                    try {
                        arrayQueue.addQueue(value);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'g':
                    try {
                        System.out.printf("取出的数据为%d", arrayQueue.getQueue());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h':
                    try {
                        System.out.printf("队列头的数据为%d", arrayQueue.headQueue());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'e':
                    loop = false;
                    scanner.close();
                    break;
            }
        }

        System.out.println("程序退出");
    }
}
