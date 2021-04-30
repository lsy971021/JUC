package com.lsy;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author lsy
 */
public class CAS {
    int a =1;
    public static void main(String[] args) {
        AtomicInteger ac = new AtomicInteger(10);
        int c;
        for (int i = 0; i < 10; i++) {
            c = ac.incrementAndGet();
            System.out.println(c);
        }
        LongAdder longAdder = new LongAdder();
    }
}
