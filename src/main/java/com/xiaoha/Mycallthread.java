package com.xiaoha;

import java.util.concurrent.Callable;

/**
 * @author: ZeKai
 * @date: 2025/4/16
 * @description:
 **/
public class Mycallthread implements Callable <Integer>{

    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName());
        return 0;
    }
}
