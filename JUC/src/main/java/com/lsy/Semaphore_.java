package com.lsy;

import org.junit.Test;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author lsy
 */
public class Semaphore_ {
    /**
     * 最多允许多少个线程同时运行
     */
    private Semaphore semaphore = new Semaphore(5,true);

    @Test
    public void test() {
        Semaphore_ s = new Semaphore_();
        for (int i = 0; i < 100; i++) {
            new Thread(s::doIt).start();
            /*new Thread(() -> {
                try {
                    semaphore.acquire(1);
//                    TimeUnit.MILLISECONDS.sleep(1500);
                    Thread.sleep(1000);
                    System.out.println("1成功" + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            }).start();
        new Thread(() -> {
            try {
                semaphore.acquire(1);
//                    TimeUnit.MILLISECONDS.sleep(1500);
                Thread.sleep(1000);
                System.out.println("2成功" + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
        }).start();*/
        }
    }

    public void doIt() {
        try {
            /**
             * 从semaphore获得一个允许
             */
            semaphore.acquire(1);
            System.out.println("succeed");
            Thread.sleep(1000);
            //TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            /**
             * 还回允许
             */
             semaphore.release(1);
        }

    }
}
