package com.xiaoha;

/**
 * @author: ZeKai
 * @date: 2025/4/16
 * @description:抢红包
 **/
public class Task4 extends Thread {
    public static void main(String[] args) {
        Task4 t1=new Task4();
        Task4 t2=new Task4();

        t1.setName("A");
        t2.setName("B");
        t1.start();
        t2.start();
    }

    static int tk=100;
    @Override
    public void run() {




            synchronized (Task4.class) {


                if(tk%2!=0)
                {
                    System.out.println(this.getName()+"成功售出，" + "剩余 " + tk + " 张票");
                    tk--;

                }
                 else
                     tk--;

            }



        super.run();
    }
}
