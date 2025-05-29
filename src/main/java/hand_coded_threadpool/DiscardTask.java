package hand_coded_threadpool;

import java.util.Objects;

/**
 * @author: ZeKai
 * @date: 2025/5/29
 * @description:
 **/
public class DiscardTask implements RejectMethod{
    @Override
    public void reject(Runnable task, MythreadPool mythreadPool) {
     //当前传进来的task是没加成功的，所以抛弃一个任务，空出空间来，然后重试这个task，总不能不管吧
        System.out.println("Discard Task:"+ Objects.requireNonNull(mythreadPool.BQ.poll()).toString());
        mythreadPool.execute(task);
    }
}
