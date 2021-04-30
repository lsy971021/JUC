package com.lsy;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author lsy
 * 读写锁
 */
public class ReadWriteLock_ {
    static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    static Lock readLock;
    static Lock writeLock;
    static ReadWriteLock_ a = new ReadWriteLock_();

    public static void main(String[] args) {
        readLock = readWriteLock.readLock();
        writeLock = readWriteLock.writeLock();
        for (int i = 0; i < 100; i++) {
            /**
             * 读锁
             */
            new Thread(()->{
                readAll(readLock);
            }).start();
            //new Thread(a::writeAll).start();
            /**
             * 写锁
             */
            new Thread(()->{
                readAll(writeLock);
            }).start();
        }
    }

    public static void readAll(Lock lock) {
        try {
            lock.lock();
            TimeUnit.SECONDS.sleep(1);
            System.out.println("完成了");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readLock.unlock();
        }
    }

    public void writeAll() {
        try {
            writeLock.lock();
            TimeUnit.SECONDS.sleep(1);
            System.out.println("写完了");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }
    }
}
