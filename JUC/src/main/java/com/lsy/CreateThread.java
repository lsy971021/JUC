package com.lsy;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lsy
 * 内部类
 */
public class CreateThread {
    public static void main(String[] args) {
        /**
         * lambda表达式或者继承Thread实现run方法
         * .start运行
         */
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

        /**
         * 实现Runnable接口，重写run方法，传入到Thread中
         * .start启动
         */
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

        /**
         * 线程池启动
         */
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
