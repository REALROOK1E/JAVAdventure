package com.xiaoha;

/**
 * @author: ZeKai
 * @date: 2025/4/16
 * @description:
 **/
public class MyThread extends Thread {
    public static ThreadLocal<Integer> num =ThreadLocal.withInitial(()->0);
    static Object obj=new Object();
    static int tk=0;
    @Override
    public void run() {


        while (tk < 100) {

            synchronized (Thread.class) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if(tk<100)
                {tk++;
                System.out.println(this.getName() + "卖了" + tk + "张票");}
            }

        }

        super.run();
    }
}
