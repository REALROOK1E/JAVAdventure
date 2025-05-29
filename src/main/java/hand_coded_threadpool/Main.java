package hand_coded_threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author: ZeKai
 * @date: 2025/5/29
 * @description:
 **/
public class Main {
    public static void main(String[] args) {

        MythreadPool mythreadPool=new MythreadPool(4,2,1000, TimeUnit.MILLISECONDS,new ArrayBlockingQueue<>(2),new DiscardTask());
        for (int i = 0; i < 5; i++) {
            mythreadPool.execute(()->{
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName());
            });
        }

        System.out.println("Main Thread runs here");
    }
}
