package com.lsy;

import java.util.*;
import java.util.concurrent.*;

/**
 * @author lsy
 * 容器
 */
public class Collections_Map {
    public static void main(String[] args) throws InterruptedException {

        /**
         * 下面的都是线程安全的
         */
        Collection<String> array = Collections.synchronizedCollection(new ArrayList<>());

        /**
         * 写时复制，写的时候才加锁，读的时候不加锁
         */
        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();


        /**
         * 下面两个执行效率差不多
         */
        Map<String, String> map = Collections.synchronizedMap(new HashMap<String, String>(100));
        Hashtable<String, String> hashtable = new Hashtable<>();


        /**
         * 在读上面效率比上面两个高，在写操作上不高，用的是synchronized
         */
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap(100);


        /**
         * 执行效率比上面的高，通过CAS(无锁操作)实现，效率比上面的高很多
         * add()方法在添加失败是抛异常，offer()方法不抛，一般用offer()
         * poll()获得元素并删除，peek()获得元素不删除
         * peek()获得元素，不删除
         */
        Queue<Integer> queue = new ConcurrentLinkedQueue<>();
        boolean add = queue.add(10);
        boolean offer = queue.offer(10);
        Integer poll = queue.poll();
        Integer peek = queue.peek();


        /**
         * 可实现订阅和发布  底层是LockSupport.park()
         * put() 添加元素，会一直添加到满为止，满了就等待
         * take() 获取元素，如果元素取完后会等待，有新元素加入就立马取出来
         */
        LinkedBlockingQueue<Integer> linkedBlockingQueue = new LinkedBlockingQueue<>(20);
        linkedBlockingQueue.put(10);
        Integer take = linkedBlockingQueue.take();


        /**
         *  同步Queue
         *  队列长度为0，只有在有take()的时候put()才能执行，来一个取一个，相当于将数据直接递给另一个take()的线程，在线程池中互相之间任务的调度都是用的这个
         *  add()方法也不能用，会报错，因为队列长度为0
         */
        BlockingQueue<String> synchronousQueue = new SynchronousQueue<>();
        new Thread(()->{
            try {
                System.out.println(synchronousQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        synchronousQueue.put("aaa");


        /**
         * 比SynchronousQueue强大
         * 跟SynchronousQueue的唯一区别是transfer()，放在队列阻塞，当取走时才继续执行，必需先启动消费者线程，相当于多个人的手递手
         * 应用场景：没有MQ下自己用Java实现：put后必需等待结果后才继续执行，否则一直等待
         */
        LinkedTransferQueue<String> linkedTransferQueue = new LinkedTransferQueue<>();
        new Thread(()->{
            try {
                System.out.println(linkedTransferQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        linkedTransferQueue.transfer("aa");


        /**
         *  内部是树实现排序
         */
        PriorityQueue<String> priorityQueue = new PriorityQueue<>();
        priorityQueue.add("a");
        priorityQueue.add("d");
        priorityQueue.add("b");
        Iterator<String> iterator = priorityQueue.iterator();
        while (iterator.hasNext()){
            System.out.println(priorityQueue.poll());
        }


        /**
         *  延迟队列，按时间进行任务调度，必需设置在当前时间之后，如下面的当前时间的1000ms后执行和2000ms后执行
         *  跟一般的queue先进先出不同，通过PriorityQueue实现的
         *  take() 取值并删除
         */
        DelayQueue<Delayed> delayeds = new DelayQueue<>();
        MyDelayed a = new MyDelayed("lsy", System.currentTimeMillis()+1000L);
        MyDelayed b = new MyDelayed("hhh", System.currentTimeMillis()+5000L);
        delayeds.put(a);
        delayeds.put(b);
        for (; ; ) {
            System.out.println(delayeds.take());
        }


    }

    static class MyDelayed implements Delayed {
        String name;
        Long time;

        public MyDelayed(String name, Long time) {
            this.name = name;
            this.time = time;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(time-System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public String toString() {
            return "MyDelayed{" +
                    "name='" + name + '\'' +
                    ", time=" + time +
                    '}';
        }

        @Override
        public int compareTo(Delayed o) {
            if (this.getDelay(TimeUnit.MILLISECONDS) < o.getDelay(TimeUnit.MILLISECONDS)) return -1;
            if (this.getDelay(TimeUnit.MILLISECONDS) > o.getDelay(TimeUnit.MILLISECONDS)) return 1;
            return 0;
        }
    }
}
