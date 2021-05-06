package com.lsy;

import java.util.concurrent.*;

/**
 * @author lsy
 */
public class ThreadPool_01 {
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

        /**
         * 更高级的Future，可以对Future管理
         * supplyAsync:异步执行
         */
        long start = System.currentTimeMillis();
        CompletableFuture<Integer> people = CompletableFuture.supplyAsync(()->priceOfPeople());
        CompletableFuture<Integer> phone = CompletableFuture.supplyAsync(()->priceOfPhone());
        CompletableFuture<Integer> car = CompletableFuture.supplyAsync(()->priceOfCar());
        /**
         * 管理任务
         * 当这三个任务都完成时才能往下执行
         * CompletableFuture.anyOf :只要这写任务中一个任务完成了就可以往下继续执行
         */
        CompletableFuture.allOf(people, phone, car).join();
        long end = System.currentTimeMillis();
        System.out.println("time:"+(end-start));
//        System.out.println("people:"+people.thenAccept(str->str.toString()).thenAccept(System.out::println));
        System.out.println("people:"+people.get());
        System.out.println("phone:"+phone.get());
        System.out.println("car:"+car.get());

        /**
         * 支持链式编程和lambda表达式
         */
        /*CompletableFuture.supplyAsync(()->priceOfPeople()).thenApply(String::valueOf)
                .thenApply(str->"price" + str)
                .thenAccept(System.out::println);*/
    }

    public static Integer priceOfPeople() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 10;
    }
    public static Integer priceOfPhone() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 5000;
    }
    public static Integer priceOfCar() {
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 1000000000;
    }
}
