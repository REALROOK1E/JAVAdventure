package com.xiaoha;

/**
 * @author: ZeKai
 * @date: 2025/4/16
 * @description:
 **/
public class MyThread extends Thread {

    @Override
    public void run() {

      for(int i=0;i<100;i++){
          System.out.println(this.getName());
          System.out.println(i);
      }
        super.run();
    }
}
