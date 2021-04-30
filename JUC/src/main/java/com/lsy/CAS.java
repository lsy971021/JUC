package com.lsy;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author lsy
 * CAS无锁优化 比synchronized快
 * 原理cas(V,Expected,NewValue)
 * V: 原值
 * Expecter: 期望值(当前的原值是多少，和原值一样的话证明没被别的线程修改，如果不一样则try again or fail)
 * NewValue: 新值
 * CAS是CPU原语支持，指令级别，中间不能被打断
 * ================================================================
 * 可能会出现ABA问题（version   V:1.0  B:2.0  A:3.0）  如果是基础类型无所谓，如果是引用类型可能出现别的线程修改了引用对象的某
 * 些值后，有回到了当前期望的值
 * 解决：AtomicStampReference  : 可察觉数据是否发生变化
 */
public class CAS {
    public static void main(String[] args) {
        /**
         * 初始值为10
         */
        AtomicInteger ac = new AtomicInteger(10);
        int c;
        for (int i = 0; i < 10; i++) {
            /**
             * 对10递增
             */
            c = ac.incrementAndGet();
            System.out.println(c);
        }
    }
}
