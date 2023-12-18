import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 计数器的使用：
 *
 * 范式如下：
 * 外围线程或等待线程：
 * CountDownLatch countDownLatch = new CountDownLatch(count);
 * threadPoolExecutor.execute(() -> runner())
 * try {
 *     countDownLatch.await(awaitTime, TimeUnit.SECONDS);
 * } catch (InterruptedException interruptedException) {
 *     interruptedException.printStackTrace();
 * }
 * 任务函数：
 * try{
 *    // Todo
 * }catch(Exception ex){
 * }finally {
 *    countDownLatch.countDown();
 * }
 *
 * */
public class CountDownLatchTest {
    private static volatile AtomicInteger count = new AtomicInteger(0);
    private static CountDownLatch countDownLatch = new CountDownLatch(10000);

    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            Thread tempThread = new Thread(new Task(countDownLatch));
            tempThread.start();
        }

        try {
            countDownLatch.await(10, TimeUnit.SECONDS);
            //countDownLatch.await();
            System.out.println("count: " + count);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }

    }

    static class Task implements Runnable{
        private final CountDownLatch countDownLatch;

        Task(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        public void run() {
            try{
                count.getAndIncrement();
            }catch(Exception ex){

            }finally {
                countDownLatch.countDown();
            }
        }
    }
}
