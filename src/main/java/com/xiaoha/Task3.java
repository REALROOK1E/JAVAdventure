package com.xiaoha;

/**
 * @author: ZeKai
 * @date: 2025/4/16
 * @description:
 **/
public class Task3 extends Thread {
    public static void main(String[] args) {
        Task3 t1=new Task3();
        Task3 t2=new Task3();

        t1.setName("A");
        t2.setName("B");
        t1.start();
        t2.start();
    }

    static int tk=100;
    @Override
    public void run() {


        while (true) {

            synchronized (Thread.class) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if(tk==0) break;
                if(tk%2!=0)
                {
                    System.out.println(this.getName()+"成功售出，" + "剩余 " + tk + " 张票");
                    tk--;

                }
                 else
                     tk--;

            }

        }

        super.run();
    }
}
