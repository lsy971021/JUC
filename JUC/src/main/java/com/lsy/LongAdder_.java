package com.lsy;

import java.util.concurrent.atomic.LongAdder;

/**
 * @author lsy
 */
public class LongAdder_ {
    public static void main(String[] args) {
        LongAdder longAdder = new LongAdder();
        for (int i = 0; i < 100; i++) {
            /**
             * 从零开始增加，在超高并发时比AtomicInteger快
             */
            longAdder.increment();
            System.out.println("longAdder===="+longAdder);
        }
    }
}
