package com.xiaoha;

import java.util.concurrent.CompletableFuture;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Start implements Runnable {

    @Override
    public void run() {
        // 线程执行的任务
        System.out.println(Thread.currentThread().getName() + " 正在执行任务");
    }
}