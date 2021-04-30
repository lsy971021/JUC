package com.lsy;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author lsy
 * 限制线程的数量，当达到某个数量线程时才执行，否则阻塞
 */
public class CyclicBarrier_ {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10, new Runnable() {
            @Override
            public void run() {
                System.out.println("满人，发车");
            }
        });
        /**
         * lambda表达式，同上面的
         */
        CyclicBarrier cyc = new CyclicBarrier(10, ()-> System.out.println("满人发车"));
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                try {
                    /**
                     * 阻塞，如果满足10个线程则执行上面重写的run方法
                     */
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
