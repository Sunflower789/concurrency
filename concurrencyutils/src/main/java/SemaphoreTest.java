import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreTest {
    private static final int THREAD_COUNT = 30;
    private static ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);
    private static Semaphore semaphore = new Semaphore(10);

    public static void main(String[] args) {
        for (int i = 0; i < THREAD_COUNT; i++) {
            threadPool.execute(new Runnable() {
                public void run() {
                    try{
                        // 获取一个许可凭证
                        semaphore.acquire();
                        System.out.println("do something");
                        TimeUnit.SECONDS.sleep(1);
                        // 归还一个许可凭证
                        semaphore.release();
                    }catch (InterruptedException interruptedException){

                    }
                }
            });
        }

        threadPool.shutdown();
    }
}
