import java.util.concurrent.TimeUnit;

/**
 *
 * 使用共享内存的方式实现线程间通信
 * 利用volatile关键字保证可见性来实现内存一致性从而实现线程间准确及时通信
 *
 * */
public class Notify2 {
    static volatile boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Thread tempThread = new Thread(new Wait(), "WaitThread-" + i);
            tempThread.start();
        }
        TimeUnit.SECONDS.sleep(1);
        Thread notifyThread = new Thread(new Notify(), "NotifyThread");
        notifyThread.start();
    }

    static class Wait implements Runnable{
        public void run() {
            while(flag){
                System.out.println("条件满足，继续等待：" + Thread.currentThread());
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
            System.out.println("条件不满足，结束：" + Thread.currentThread());
        }
    }

    static class Notify implements Runnable{
        public void run() {
            System.out.println("获取到锁，修改条件：" + Thread.currentThread());
            flag = false;
        }
    }
}
