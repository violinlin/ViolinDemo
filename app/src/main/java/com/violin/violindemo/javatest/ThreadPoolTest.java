package com.violin.violindemo.javatest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

    public static void main (String []arg){


//        cachedThreadPool();
//        fixedThreadPool();

//        scheduledThreadPool();

        singleThreadExecutor();

    }
//创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
    private static void cachedThreadPool(){

        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        for (int i=0; i<10; i++){

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            final int index = i;

            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("index:"+index+"\t"+Thread.currentThread().getName());

                }
            });

        }


    }

// 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待
    private static void fixedThreadPool(){

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);

        for (int i=0; i<10 ; i++){

            final int index = i;
            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {

                    System.out.println("index:"+index+"\t"+Thread.currentThread().getName());

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
            });


        }


    }


//创建一个定长线程池，支持定时及周期性任务执行。
    private static void scheduledThreadPool(){
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        scheduledThreadPool.schedule(new Runnable() {
            public void run() {
                System.out.println("delay 3 seconds");
            }
        }, 3, TimeUnit.SECONDS);

    }

    private static void singleThreadExecutor(){
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

        for (int i=0; i<10; i++){

            final  int index =i;

            singleThreadExecutor.execute(new Runnable() {
                @Override
                public void run() {

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("index:"+index+"\t"+Thread.currentThread().getName());

                }
            });


        }

    }



}
