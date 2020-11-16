package com.wushiyuan.com.datastructures.String;

import org.junit.jupiter.api.Test;

public class StringTest {
    //String：字符串，使用一对""引起来表示
    //1、String 声明为 final 的，不可被继承
    //2、String 实现了 Serializable 接口：表示字符串是支持序列化的
    //          实现了 Comparable 接口，表示 String 是可以比较大小的
    //3、内部定义了 final char[] value 用于存储字符串数据
    //4、代表不可变的字符序列，简称：不可变性
    //      1、当对字符串重新赋值时，需要重写指定内存区域赋值，不能使用原有的 value 进行赋值
    //      2、当对现有的字符串进行链接操作时，也需要重新指定内存区域赋值，不能使用原有的 value 进行赋值
    //      3、当调用 String 的 replace 方法修改指定字符或字符串时，也需要重新指定内存区域赋值，不能使用原有的 value 进行赋值
    //5、通过字面量的方式（区别于new）给一个字符串赋值，此时的字符串声明在字符串常量池中。
    //6、字符串常量池是不会存储相同内容的字符串的
    @Test
    void testString() {
//        System.out.println("2345");
        String s1 = "abc";
        String s2 = "abc";

        s1 = "hello";

        System.out.println(s1 == s2);   //比较 s1 和 s2 的地址值

        System.out.println(s1);
        System.out.println(s2);
        
        System.out.println("***********************");

        String s3 = s1 + "def";

        System.out.println(s3);
        System.out.println(s1);

        System.out.println("***********************");

        String s4 = "abc";
        String s = s4.replace('a', 'm');
        System.out.println(s);
    }
}
