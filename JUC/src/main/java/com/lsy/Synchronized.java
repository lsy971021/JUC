package com.lsy;

import java.util.concurrent.TimeUnit;

/**
 * @author lsy
 */
public class Synchronized {
    public static void main(String[] args) {
        Synchronized s = new Synchronized();
        new Thread(()->{
            try {
                s.test01();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{
            try {
                s.test02();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
    public synchronized void test01() throws InterruptedException {
        System.out.println("test01 start...");
        //Thread.sleep(4000);
        TimeUnit.SECONDS.sleep(4);
        System.out.println("test01 end...");
    }

    public void test02() throws InterruptedException {
        //Thread.sleep(2000);
        TimeUnit.SECONDS.sleep(2);
        System.out.println(Thread.currentThread().getName()+"test02");
    }
}
