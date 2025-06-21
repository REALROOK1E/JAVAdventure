package hand_coded_threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author: ZeKai
 * @date: 2025/5/29
 * @description:
 **/
public class MythreadPool {

/*集合？no
为了避免轮询消耗cpu资源，使用阻塞队列
最开始 给让单个线程遍历task集合
但是显然单个线程不够，所以直接搞一个集合存线程
然后把Tread执行任务的逻辑抽象到Runnable里？
此时思考：这个线程集合要多大？
定义一个核心线程数，这个是一个基本盘
在execute里，当list里面没这么多时候，就创建新线程加到核心线程集合里面,并且要启动！线程创建完都要启动

因为这两种线程的性质不同，所以分开定义
直接用内部类把这俩线程封装成两个类，用继承Thread的方式

等待多久？
  * */

    public MythreadPool(int max_size, int coreSize, int timeout, TimeUnit timeUnit, BlockingQueue<Runnable> BQ,RejectMethod rejectMethod) {
        this.max_size = max_size;
        this.coreSize = coreSize;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.BQ=BQ;
        this.rejectMethod=rejectMethod;

    }

    private final int max_size;
    private final int coreSize;
    private final int timeout;
    private TimeUnit timeUnit;
    public final BlockingQueue<Runnable> BQ;//这是所有执行任务的列表


    private RejectMethod rejectMethod;

    List<Thread> coreList=new ArrayList<>();
    List<Thread> supList=new ArrayList<>();



    void execute(Runnable task){
        if(coreList.size()<coreSize){
            Thread thread=new coreThread(task);

            coreList.add(thread);
            thread.start();
        }
        //原子操作，或者加锁，否则这里有线程安全
        if(BQ.offer(task)) return;

        if(coreList.size()+supList.size()<max_size){
                Thread thread=new supThread(task);
                supList.add(thread);
                thread.start();
        }
        if(!BQ.offer(task)) rejectMethod.reject(task,this);


    }

    class coreThread extends Thread{
        private final Runnable firstTask;
        public coreThread(Runnable task) {
            this.firstTask=task;
        }

        @Override
        public void run() {
            while(true){
                  try {
                Runnable task=BQ.take();//这里take方法是一直阻塞
                task.run();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        }
    }
    class supThread extends Thread{
        private final Runnable firstTask;
        public supThread(Runnable task) {
            this.firstTask=task;
        }

        @Override
        public void run() {
            while(true){
                try {
                    Runnable task=BQ.poll(timeout, timeUnit);//这里要用poll方法定时销毁，因为辅助线程不是常态
                    if(task==null){
                        System.out.println("sup thread "+Thread.currentThread().getName()+"is destroyed");
                       supList.remove(Thread.currentThread());//当然要移除
                        break;
                    }
                    task.run();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
