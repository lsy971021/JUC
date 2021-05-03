package com.lsy;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author lsy
 * LockSupport和wait
 * LockSupport:不用加在某把锁上，unpark()可以先于park()执行，使其执行时不用阻塞
 * wait需要加在某把锁上，像synchronized一样，而且notify()先于wait()执行,不能使wait()无效。
 */
public class LockSupport_ {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                if (i == 5) LockSupport.park();
                /*if (i == 5) {
                    try {
                        LockSupport_.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }*/
                System.out.println("t线程执行到"+i);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        System.out.println("主线程休息8秒");
        TimeUnit.SECONDS.sleep(8);
        LockSupport.unpark(t);
        //LockSupport_.class.notify();
    }
}
