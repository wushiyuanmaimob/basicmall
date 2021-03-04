package com.wushiyuan.com.datastructures.String;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

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

    //测试字符串的不可变性
    @Test
    void 测试字符串不可变性() {
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

    @Test
    void 测试不同实例化方式的对比() {
        String s1 = "abc";
        String s2 = "abc";
        String s3 = "abc";
        String s4 = "abc";

        System.out.println(s1 == s2);
        System.out.println(s1 == s3);
        System.out.println(s1 == s4);
        System.out.println(s3 == s4);

        Person tomOne = new Person(10, "tom");
        Person tomTwo = new Person(10, "tom");

        System.out.println(tomOne.getName() == tomTwo.getName());
        tomOne.setName("jerry");
        System.out.println(tomTwo.getName());
    }

    @Test
    void 测试不同拼接操作的对比() {
        String s1 = "javaEE";
        String s2 = "hadoop";

        String s3 = "javaEEhadoop";
        String s4 = "javaEE" + "hadoop";
        String s5 = s1 + "hadoop";
        String s6 = "javaEE" + s2;
        String s7 = s1 + s2;

        System.out.println(s3 == s4);   //true
        System.out.println(s3 == s5);   //false
        System.out.println(s3 == s6);   //false
        System.out.println(s3 == s7);   //false
        System.out.println(s5 == s6);   //false
        System.out.println(s5 == s7);   //false
        System.out.println(s6 == s7);   //false

        String s8 = s5.intern();    //返回值得到 s8 使用的常量值中已经存在的 “javaEEhadoop”
        System.out.println(s3 == s8);   //true
    }

    class Person {
        private int age;
        private String name;

        public int getAge() {
            return age;
        }

        public String getName() {
            return name;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Person(int age, String name) {
            this.age = age;
            this.name = name;
        }
    }

    @Test
    void 测试String的一道面试题() {
        TestString ex = new TestString();
        ex.change(ex.str, ex.ch);

        System.out.println(ex.str);
        System.out.println(ex.ch);
    }

    class TestString {
        String str = "goods";
        char[] ch = {'t', 'e', 's', 't'};

        public void change(String str, char[] ch) {
            str = "test ok";
            ch[0] = 'b';
        }
    }

    @Test
    void 测试字符串的常用方法() {
        String str = "HelloWorld";
        //length()
        System.out.println(str.length());
        //charAt()
        System.out.println(str.charAt(0));
        System.out.println(str.charAt(1));
        //isEmpty()
        System.out.println(str.isEmpty());
        //toLowerCase()
        String s2 = str.toLowerCase();
        System.out.println(str);
        System.out.println(s2);
        //trim()
        String s3 = "  a b c  ";
        String s4 = s3.trim();
        System.out.println(s3);
        System.out.println(s4);
        //equals()
        System.out.println(s3.equals(s4));
        //equalsIgnore()
        System.out.println(str.equalsIgnoreCase(s2));
        //concat()
        System.out.println(s3.concat("def"));
        //compareTo()
        String s5 = "abc";
        String s6 = "abe";
        int compare = s5.compareTo(s6);
        System.out.println(compare);
        //substring() 左闭右开
        System.out.println(s5.substring(0, 2));
        //endWith()
        String a = "helloWorld";
        boolean ld = a.endsWith("ld");
        System.out.println(ld);
        //startWith()
        boolean hel = a.startsWith("hel");
        System.out.println(hel);
        //
        boolean ll = a.startsWith("ll", 2);
        System.out.println(ll);
        //contains()
        String b = "or";
        boolean contains = a.contains(b);
        System.out.println(contains);
        //indexOf()
        int o = a.indexOf("o");
        System.out.println(o);
        int a1 = a.indexOf("a");
        System.out.println(a1);
        //
        int el = a.indexOf("el");
        System.out.println(el);
        //lastIndexOf()
        String str3 = "hellorworld";
        int or = str3.lastIndexOf("or", 6);
        System.out.println(or);
        //什么情况下，indexOf(str)和lastIndexOf(str)返回值相同==》字符串只有一个字符
        //replace()
        String str4 = "北京属于中国北京";
        String replace = str4.replace('北', '东');
        System.out.println(str4);
        System.out.println(replace);
        //replaceAll()
        String str6 = "12hello34world5java7891mysql1456";
//        String s = str6.replaceAll("\\d+", ",");
        String s = str6.replaceFirst("\\d+", ",");
        System.out.println(s);
        //matches()
        String str7 = "2345";
        boolean matches = str7.matches("\\d+");
        System.out.println(matches);
        //split
        String str8 = "hello|world|java";
        String[] split = str8.split("\\|");
        for (String s1 : split) {
            System.out.println(s1);
        }
    }

    /**
     * String 与基本数据类型、包装类之间的转换
     *
     * String --> 基本数据类型、包装类：调用包装类的静态方法：parseXxx(str)
     * 基本数据类型、包装类 --> String：调用 String 重载的 valueOf(xxx)
     *
     * String --> char[]：调用 String 的 toCharArray()
     * char[] --> String：调用 String 的构造器
     *
     * 编码：字符串-->字符（看得懂--->看不懂的二进制数据）
     * 解码：编码的逆过程，字节 --> 字符串（看不懂的二进制数据 ---> 看得懂的）
     *
     * String 和 byte[]之间的转换
     * 编码 ： String --> byte[]：调用 String 的 getBytes()
     * 解码 ： byte[] --> String：调用 String 的构造器
     *
     * 说明：解码时，要求解码使用的字符集必须与编码时使用的字符集一致，否则会出现乱码。
     *
     */
    @Test
    void 测试String与基本类型包装类的转换() throws UnsupportedEncodingException {
        String str1 = "123";
        int anInt = Integer.parseInt(str1);
        System.out.println(anInt);
        String s = String.valueOf(anInt);
        System.out.println(s);
        System.out.println(str1 == s);

        String s1 = "abc123";
        char[] charArray = s1.toCharArray();
        for (char c : charArray) {
            System.out.println(c);
        }

        char[] arr = {'h', 'e', 'l', 'l', 'o'};
        String s2 = new String(arr);
        System.out.println(s2);

        String s4 = "abc123中国";
        byte[] s4Bytes = s4.getBytes();
        System.out.println(Arrays.toString(s4Bytes));
        byte[] gbks = s4.getBytes("gbk");
        System.out.println(Arrays.toString(gbks));

        String s3 = new String(s4Bytes);
        System.out.println(s3); //使用默认的字符集，进行编码
        String s5 = new String(gbks);
        System.out.println(s5); //出现乱码，原因：编码集和解码集不一致！
        String s6 = new String(gbks, "gbk");
        System.out.println(s6); //没有出现乱码，原因：编码集合解码集一致！
    }

    @Test
    void 测试一个拼接题() {
        String s1 = "javaEEhadoop";
        String s2 = "javaEE";
        String s3 = s2 + "hadoop";
        System.out.println(s1 == s3);   //false
        
        final String s4 = "javaEE";     //常量
        String s5 = s4 + "hadoop";
        System.out.println(s1 == s5);   //true
    }

    /**
     * 测试常见的String算法题目
     */
    @Test
    void questionOne() {
        String str = "  a b c   ";
        System.out.println(myTrim(str));

        String str2 = "abcdefg";
        String reverse = myReverse(str2, 2, 6);
        System.out.println(reverse);

        //“ab”在“abkkcadkabkebfkabkskab
        String s1 = "ab";
        String s2 = "abkkcadkabkebfkabkskab";
        System.out.println(getAStrInOtherStrTimes(s1, s2));
    }

    /**
     * 模拟一个 trim 方法，去除字符串两端的空格
     * @param str
     * @return
     */
    public String myTrim(String str) {
        char[] chars = str.toCharArray();
        int start = 0;
        int end = chars.length - 1;
        while (start <= end) {
            if (chars[start] > 32) {
                break;
            }
            start ++;
        }
        while (end > 0) {
            if (chars[end] > 32) {
                break;
            }
            end --;
        }

        return str.substring(start, end + 1);
    }

    /**
     * 将一个字符串进行反转。比如"abcdefg"反转为“abfedcg”
     * @param str
     * @return
     */
    public String myReverse(String str, Integer start, Integer end) {
        char[] chars = str.toCharArray();
        char[] newChars = new char[end - start + 1];
        int index = 0;
        int autoIndex = 0;
        for (char aChar : chars) {
            if (index >= start && index < end) {
                newChars[autoIndex] = aChar;
                autoIndex ++;
            }
            index ++;
        }
        char[] reversedChars = new char[newChars.length];
        int length = newChars.length - 1;
        int i = 0;
        while (length >= 0) {
            reversedChars[i] = newChars[length];
            length --;
            i ++;
        }
        String reversedStr = new String(reversedChars);

        String startStr = str.substring(0, start);
        String endStr = str.substring(end, str.length());

        return String.format("%s%s%s", startStr, reversedStr, endStr);
    }

    /**
     * 获取一个字符串str在另一个字符串otherStr中出现的次数
     * @param str
     * @param otherStr
     * @return
     */
    public int getAStrInOtherStrTimes(String str, String otherStr) {
        int count = 0;
        int index = 0;

//        while ((index = otherStr.indexOf(str)) != -1) {
//            count ++;
//            otherStr = otherStr.substring(index + str.length());
//        }

        while ((index = otherStr.indexOf(str, index)) != -1) {
            count ++;
            index += str.length();
        }

        return count;
    }



    /**
     * String、StringBuffer、StringBuilder三者的异同
     * String：不可变字符序列；底层使用char[]
     * StringBuffer：可变的字符序列；线程安全的，效率低；底层使用char[]
     * StringBuilder：jdk5.0新增；可变的字符序列；线程不安全，效率高；底层使用char[]
     *
     * 源码分析：
     * String str = new String();//char[] value = new char[0];
     * String str1 = new String("abc");//char[] value = new char[]{'a', 'b', 'c'};
     *
     * StringBuffer sb1 = new StringBuffer();//char[] value = new char[16];底层创建了一个长度是16的字符数组
     * System.out.println(sb1.length()) //0
     * sb1.append('a');//value[0] = 'a';
     * sb1.append('b');//value[1] = 'b';
     *
     * StringBuffer sb2 = new StringBuffer("abc");//char[] value = new char["abc".length() + 16]
     *
     * 问题1、System.out.println(sb2.length)   //3
     * 问题2、扩容问题：如果要添加的数据底层数组盛不下了，那就需要扩容底层的数组。
     * 默认情况下，扩容为原来容量的 2倍 + 2，同时将原有数组中的元素复制到新的数组中
     *
     * 指导意义：开发中建议大家使用：StringBuffer(int capacity)或StringBuilder(int capacity)
     *
     *
     */
    @Test
    void 测试StringBufferStringBuilder() {

    }

    /**
     * StringBuffer的常用方法
     * StringBuffer append(xxx)：用于进行字符串拼接
     * StringBuffer delete(int start, int end)：删除指定位置的内容
     * StringBuffer replace(int start, int end, String str)：把[start, end]位置替换成 str
     * StringBuffer insert(int offset, xxx)：在指定位置插入xxx
     * StringBuffer reverse()：把当前字符序列逆转
     *
     * public int indexOf(String str)
     * public String substring(int start, int end)：返回一个从start开始到end索引结束的左闭右开区间的子字符串
     * public int length()
     * public char charAt(int n)
     * public void setCharAt(int n, char ch)
     *
     * 总结：
     * 增 append
     * 删 delete
     * 改 setCharAt replace
     * 查 charAt
     * 插 insert
     * 长度 length
     * 遍历 for + charAt
     *
     */
    @Test
    void 测试StringBuffer的常用方法() {
        StringBuffer s1 = new StringBuffer();
        s1.append(1);
        s1.append('1');
        s1.append("abc");

        System.out.println(s1);
        System.out.println(s1.substring(2, 3));
        System.out.println(s1.length());
        System.out.println(s1.charAt(2));
        s1.setCharAt(0, '9');
        System.out.println(s1);
    }
    
    @Test
    void 测试StringBuilderBuffer三个函数的执行效率() {
        long startTime = 0l;
        long endTime = 0l;
        String text = "";
        StringBuffer buffer = new StringBuffer();
        StringBuilder builder = new StringBuilder();

        //开始对比
        //buffer
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 200000; i++) {
            buffer.append(i);
        }
        endTime = System.currentTimeMillis();
        System.out.println("buffer花费时间：" + (endTime - startTime));

        //builder
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 200000; i++) {
            builder.append(i);
        }
        endTime = System.currentTimeMillis();
        System.out.println("builder花费时间：" + (endTime - startTime));

        startTime = System.currentTimeMillis();
        for (int i = 0; i < 200000; i++) {
            text += String.valueOf(i);
        }
        endTime = System.currentTimeMillis();
        System.out.println("string 拼接花费时间：" + (endTime - startTime));
    }
}
