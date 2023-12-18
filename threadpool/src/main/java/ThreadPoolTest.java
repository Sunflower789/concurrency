import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池的使用
 *
 * */
public class ThreadPoolTest {
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 5, 3L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(5),
                //new CustomizableThreadFactory("thread-name"),
                new ThreadFactoryBuilder().setNameFormat("Thread-task-%d").build(),
                new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.execute(new Runnable() {
                public void run() {
                    try {
                        TimeUnit.MILLISECONDS.sleep(200);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "     " + Thread.currentThread().getId());
                }
            });
        }

        TimeUnit.SECONDS.sleep(3);
        threadPoolExecutor.shutdown();
    }
}
