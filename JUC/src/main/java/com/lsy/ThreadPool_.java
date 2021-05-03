package com.lsy;

import java.util.concurrent.*;

/**
 * @author lsy
 */
public class ThreadPool_ {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /**
         * Callable和Runnable区别：Callable有返回值
         */
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "hello ";
            }
        };
        ExecutorService service = Executors.newCachedThreadPool();
        /**
         * 异步执行，把callable扔给submit后继续执行main方法
         * callable是一个任务，执行完需要用Future来存储的
         */
        Future<String> future = service.submit(callable);
        /**
         * 阻塞，当能get到值时才返回，否则一直阻塞
         */
        System.out.println(future.get());
        service.shutdown();


        /**
         * FutureTask本来是一个任务，也能存储
         * Future和Runnable结合，更合适
         */
        FutureTask<Integer> futureTask = new FutureTask(()->{
            TimeUnit.SECONDS.sleep(2);
            return 1000;
        });

        new Thread(futureTask).start();
        System.out.println(futureTask.get());
    }
}
