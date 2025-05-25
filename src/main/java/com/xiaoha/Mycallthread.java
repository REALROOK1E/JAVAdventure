package com.xiaoha;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author: ZeKai
 * @date: 2025/4/16
 * @description: 三个人抢红包，输出手气最佳的人名字
 **/
public class Mycallthread implements Callable <Double>{

   final static double Min_money=0.01;
   Double red_pack=100.0;
   static int peoplenum=3;
    double get_money=0.0;

    @Override
    public Double call() throws Exception {
        return getaDouble();
    }

    private synchronized Double getaDouble() {
        System.out.println(Thread.currentThread().getName());
        Random r=new Random();
        if(peoplenum==1) {
            System.out.println(Thread.currentThread().getName()+"got: "+red_pack);

            return red_pack;

        }
        peoplenum--;
        get_money=r.nextDouble(Min_money,red_pack-(Min_money*peoplenum));
        red_pack-=get_money;
        System.out.println(Thread.currentThread().getName()+"got: "+get_money);

        return get_money;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Mycallthread mycallthread=new Mycallthread();
        FutureTask<Double> ft1=new FutureTask<>(mycallthread);
        FutureTask<Double> ft2=new FutureTask<>(mycallthread);
        FutureTask<Double> ft3=new FutureTask<>(mycallthread);
        HashMap<Double,String> map=new HashMap<>();
        Thread t1 = new Thread(ft1,"A");
        Thread t2 = new Thread(ft2,"B");
        Thread t3 = new Thread(ft3,"C");

         t1.start();
         t2.start();
         t3.start();
        map.put(ft1.get(), t1.getName());//get的阻塞特性导致他在得到结果前会阻塞
        map.put(ft2.get(), t2.getName());
        map.put(ft3.get(), t3.getName());
       System.out.println(  map.get(Collections.max(map.keySet()))+"got most money");
    }
}
