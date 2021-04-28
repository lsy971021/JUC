package com.lsy;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lsy
 */
public class Demo {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (int i = 1; i < 110; i++) {
                System.out.println("1号线程第" + i + "次执行");
            }
        });


        Thread t2 = new Thread(() -> {
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 1; i < 110; i++) {
                System.out.println("2号线程第" + i + "次执行");
            }
        });

        Thread t3 = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            t2.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        for (int i = 1; i < 110; i++) {
                            System.out.println("3号线程第" + i + "次执行");
                        }
                    }
                }
        );
        ExecutorService excu = Executors.newFixedThreadPool(3);
        excu.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {

                for (int i = 1; i < 110; i++) {
                    System.out.println("4号线程第" + i + "次执行");
                }
                return true;
            }
        });
        excu.shutdown();
        t2.start();
        t1.start();
        t3.start();

    }
}
