package com.xiaoha;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

//        MyThread t1 = new MyThread();
//        t1.setName("线程1");
//        t1.start();
//
//        MyThread t2 = new MyThread();
//        t2.setName("线程2");
//        t2.start();
//
//        t2.setPriority(1);
//        t1.setPriority(5);
//        Start s = new Start();
//        Thread t3 = new Thread(s);
//        t3.setName("sd");
//        // 启动线程
//        t3.start();

        Mycallthread mycallthread=new Mycallthread();
        FutureTask<Integer> ft=new FutureTask<>(mycallthread);
        Thread t4 = new Thread(ft,"欧内的手");

        // 启动线程
        t4.start();
        System.out.println(ft.get());














    }
}