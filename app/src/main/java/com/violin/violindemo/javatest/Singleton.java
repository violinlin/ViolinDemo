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

        if (singleton == null){// 例如线程a、b都执行到了此处 。 如果b进入了同步代码块，又有c线程调用getInstance,外层判断可以不等b释放锁，直接返回singleton
            synchronized (Singleton.class){// 线程a先获取锁，执行初始化逻辑，赋值后释放锁
                if (singleton == null){//线程a释放锁后，线程b获得锁，进入同步代码块，此时singleton已经被a线程赋值，直接返回singleton，
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
