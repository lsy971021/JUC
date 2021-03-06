package com.lsy;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lsy
 */
public class ReentrantLock_ {
    static Lock lock = new ReentrantLock();
    static ReentrantLock_ reentrantLock_ = new ReentrantLock_();
    public static void main(String[] args) {
        Thread t1 = new Thread(reentrantLock_::t1);
        Thread t2 = new Thread(reentrantLock_::t2);
        t2.start();
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(3);
                /**
                 * 3秒后打断线程t2，往下执行   (线程里有lock.lockInterruptibly());
                 */
                t2.interrupt();
                System.out.println("interrupt()执行结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void t1() {
        try {
            lock.lock();
            System.out.println("t1获得锁,休息5秒");
//            Thread.sleep(5000);
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void t2(){
        boolean b = false;
        try {
            System.out.println("lock.lockInterruptibly()被调用===");
            /**
             * 尝试获在2秒内获得锁，如果没有获得返回false
             */
//            b = lock.tryLock(2, TimeUnit.SECONDS);
            /**
             * 获得一个可以被打断的锁,可以对interrupt()方法响应
             */
            lock.lockInterruptibly();
            TimeUnit.SECONDS.sleep(5000);
            System.out.println("t2=="+b);
        } catch (Exception e) {
            System.out.println("t2被中断了");
        } finally {
            if(b)lock.unlock();
        }
    }
}
