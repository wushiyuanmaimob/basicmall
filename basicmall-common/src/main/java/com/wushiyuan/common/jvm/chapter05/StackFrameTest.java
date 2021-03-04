package com.wushiyuan.common.jvm.chapter05;

/**
 * 方法结束有两种情况：1、正常 return 2、方法未捕获异常，程序终止
 *
 * 编译后进去 class 文件，执行 javap -v StackFrameTest 可以看到字节码解析
 */
public class StackFrameTest {
    public static void main(String[] args) {
        System.out.println("主线程...");

        StackFrameTest frameTest = new StackFrameTest();

        frameTest.method1();
    }

    public double method1() {
        System.out.println("method1 方法执行开始");
        method2();
        System.out.println("method1 方法执行结束");

        return 20.2;
    }

    public void method2() {
        System.out.println("method2 方法执行开始");
        method3();
        System.out.println("method2 方法执行结束");
    }

    public void method3() {
        System.out.println("method3 方法执行开始");
        System.out.println("method3 方法执行结束");
    }


}
