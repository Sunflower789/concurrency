import java.util.Map;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * 同步屏障
 *
 * 让一个线程到达一个屏障时被阻塞，直到最后一个线程到达屏障时，屏障才会打开，所有被屏障拦截的线程才会继续执行。
 * 所以需要特别注意的是：如果屏障个数设置大于线程到达屏障数，所有线程会一直阻塞不会自动释放掉的。
 * CyclicBarrier多用于汇总计算结果等场景。
 *
 * */
public class CyclicBarrierTest {
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(5,new End());

    private static ConcurrentHashMap<String,Integer> resultMap = new ConcurrentHashMap<String, Integer>();

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 4; i++) {
            Thread tempThread = new Thread(new Task(), "Thread-" + i);
            tempThread.start();
        }

        // 此处慎用getNumberWaiting()和isBroken()方法
        TimeUnit.MILLISECONDS.sleep(200);
        while (cyclicBarrier.getNumberWaiting() > 0 ){
            Thread tempThread = new Thread(new Task(), "Thread-add");
            tempThread.start();
            System.out.println("等待中");
            TimeUnit.MILLISECONDS.sleep(200);
        }
        System.out.println("任务结束或被中断");
    }

    static class Task implements Runnable{
        public void run() {
            try {
                Random random = new Random();
                int num = random.nextInt(10);
                resultMap.put(Thread.currentThread().getName(),num);
                System.out.println("num is : " + num);
                TimeUnit.MILLISECONDS.sleep(100);
                // 插入一个内存屏障
                cyclicBarrier.await();    //可以加超时时间：cyclicBarrier.await(10,TimeUnit.SECONDS)
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    static class End implements Runnable{
        public void run() {
            int total = 0;
            for (Map.Entry<String,Integer> entry : resultMap.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                total = total + value;
                System.out.println(key + " : " + value);
            }
            System.out.println("total : " + total);
        }
    }
}
