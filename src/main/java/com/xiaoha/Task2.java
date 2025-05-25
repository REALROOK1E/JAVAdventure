package com.xiaoha;

/**
 * @author: ZeKai
 * @date: 2025/4/16
 * @description:
 **/
public class Task2 extends Thread {
    public static void main(String[] args) {
        Task2 t1=new Task2();
        Task2 t2=new Task2();

        t1.setName("A");
        t2.setName("B");
        t1.start();
        t2.start();
    }

    static int tk=100;
    @Override
    public void run() {


        while (true) {
            int i=1;
            synchronized (Thread.class) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if(tk>10)
                {tk--;
                    System.out.println(this.getName()+"成功售出，" + "剩余 " + tk + " 张票");}
                 else break;

            }

        }

        super.run();
    }
}
