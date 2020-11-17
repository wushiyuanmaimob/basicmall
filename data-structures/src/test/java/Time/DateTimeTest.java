package Time;

import org.junit.jupiter.api.Test;

import java.util.Date;

/**
 * JDK 8之前日期和时间的API测试
 */
public class DateTimeTest {
    @Test
    void test1() {
        //返回当前时间与1970年1月1日0时0分0秒之间以毫秒为单位的时间差
        //称为时间戳
        long l = System.currentTimeMillis();
        System.out.println(l);
    }

    /**
     * java.util.Date类
     *      |---java.sql.Date类
     *
     * 1.两个构造器的使用
     *
     * 2.两个方法的使用
     *      toString()  显示当前的年月日时分秒
     *      getTime()   获取当前 Date 对象对应的毫秒数
     *
     * 3.java.sal.Date 对应数据库中的日期类型变量
     *      如何实例化
     *      sql.Date ---> util.Date
     *      如何将 java.util.Date 对象转换为 java.sql.Date 对象
     */
    @Test
    void test2() {
        //构造器一：Date()：创建当前时间的Date对象
        Date date1 = new Date();
        
        System.out.println(date1.toString());
        
        System.out.println(date1.getTime());
        
        //构造器二：创建指定好描述的Date对象
        Date date2 = new Date(1605581355718L);
        System.out.println(date2.toString());

        //创建 java.sql.Date 对象
        java.sql.Date date3 = new java.sql.Date(1605581355718L);
        System.out.println(date3);

        //如何将 java.util.Date 对象转换为 java.sql.Date 对象
        Date date4 = new Date();
        java.sql.Date date5 = new java.sql.Date(date4.getTime());
        System.out.println(date5);
    }
}
