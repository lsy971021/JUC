package com.lsy;

import java.util.concurrent.TimeUnit;

/**
 * @author lsy
 * 线程之间变量不共享
 * 线程隔离
 */
public class ThreadLocal_ {
    private static int a = 100;
    static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            //threadLocal.set(a);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(threadLocal.get());
        });
        t1.start();


        Thread t2 = new Thread(() -> {
            threadLocal.set(a+100);
        });
        t2.start();
    }
}
