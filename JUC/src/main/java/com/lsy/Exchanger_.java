package com.lsy;

import java.util.concurrent.Exchanger;

/**
 * @author lsy
 * 线程间数据交换
 */
public class Exchanger_ {
    static Exchanger<String> exchanger = new Exchanger<>();
    public static void main(String[] args) {
        new Thread(()->{
            try {
                String s = "我来自一号";
                /**
                 * exchange里有两个位置
                 * 当执行exchange方法时将数据传到一个位置上并阻塞，等另一个位置也别占时才执行
                 * 跟CyclicBarrier挺像的
                 */
                String exchange = exchanger.exchange(s);
                System.out.println(Thread.currentThread().getName()+"号线程:  "+exchange);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"一").start();


        new Thread(()->{
            try {
                String s = "我来自二号";
                String exchange = exchanger.exchange(s);
                System.out.println(Thread.currentThread().getName()+"号线程:  "+exchange);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"二").start();
    }
}
