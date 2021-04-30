package com.lsy;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author lsy
 * 线程调用synchronized方法也不会影响别的线程执行别的方法
 */
public class Synchronized {
    //@Test
    public static void main(String[] args) {
        Synchronized s = new Synchronized();
        new Thread(() -> {
            try {
                test01();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                s.test02();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * synchronized锁的是this
     * 当一个线程获取对象锁之后，这个线程可以再次获取本对象上的锁
     * 可重入锁的意义在于防止死锁。
     * <p>
     * 当一个线程调用当前这个类（this）的test01时，可以得到this类的锁，调用test时候可以再次获得this类的锁
     *
     * @throws InterruptedException
     */
    public static synchronized void test01() throws InterruptedException {
        System.out.println("test01 start...");
        //Thread.sleep(4000);
        TimeUnit.SECONDS.sleep(4);
        /**
         * 可重入锁
         */
        test();
//        test0();
        System.out.println("test01 end...");
    }

    public static synchronized void test() throws InterruptedException {
        System.out.println("test执行了");
    }

    /**
     * 测试用
     * @throws InterruptedException
     */
    public static void test0() throws InterruptedException {
        synchronized (new Object()){
            System.out.println("test0执行了");
        }
    }

    public void test02() throws InterruptedException {
        //Thread.sleep(2000);
        TimeUnit.SECONDS.sleep(2);
        System.out.println(Thread.currentThread().getName() + "test02");
    }
}
