package com.violin.violindemo.javatest;

import io.reactivex.Single;

/**
 * 单利模式
 */

public class Singleton {

    private volatile static Singleton singleton;


    private Singleton() {

    }

    /**
     * 单例方式1
     * double-check机制
     * @return
     */
    public static Singleton getInstance(){

        if (singleton == null){
            synchronized (Singleton.class){
                if (singleton == null){
                    singleton = new Singleton();
                }
            }
        }

        return singleton;
    }

    /**
     * 静态内部类方法
     */

    public static Singleton getInstance2(){
        return InnerInstance.instance;
    }
    private static class InnerInstance{
        private static final Singleton instance = new Singleton();

    }






}
