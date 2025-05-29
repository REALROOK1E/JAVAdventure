package hand_coded_threadpool;

/**
 * @author: ZeKai
 * @date: 2025/5/29
 * @description:
 **/
public interface RejectMethod {
    void reject(Runnable task,MythreadPool mythreadPool);
}
