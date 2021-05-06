package com.lsy;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author lsy
 */
public class ThreadPool_02 {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,10,20, TimeUnit.SECONDS
                ,new LinkedBlockingQueue<>(200), Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
        threadPoolExecutor.execute(()->{
            System.out.println(Thread.currentThread().getName());
            System.out.println("hello");
        });
    }
}
