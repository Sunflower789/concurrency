import java.util.concurrent.TimeUnit;

/**
 * 使用锁的方式实现线程间通信
 * 利用synchronized关键字（monitor对象锁）实现线程间通信
 *
 * 范式如下：
 * 等待方：
 * synchronized(对象){
 *     while(判断条件){
 *         对象.wait()
 *     }
 * }
 * 通知方
 * synchronized(){
 *     修改条件
 *     对象.notifyAll();  // 对象.notify();
 * }
 *
 * notify()和notifyAll()的区别：
 * 关注下面注释的代码行，执行后很快就能注意到区别。
 * notify()只有通知一个线程（最早等待）；notifyAll()会通知所有等待线程竞争，执行顺序不一定。
 *
 * */
public class Notify1 {

    static boolean flag = true;
    static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {
//        Thread waitThread = new Thread(new Wait(), "WaitThread");
//        waitThread.start();
        for (int i = 0; i < 5; i++) {
            Thread tempThread = new Thread(new Wait(), "WaitThread-" + i);
            tempThread.start();
        }
        TimeUnit.SECONDS.sleep(1);
        Thread notifyThread = new Thread(new Notify(), "NotifyThread");
        notifyThread.start();
    }

    static class Wait implements Runnable{
        public void run() {
            synchronized (LOCK){
                while(flag){
                    System.out.println("条件满足，继续等待：" + Thread.currentThread());
                    try {
                        LOCK.wait();
                        // wait()将导致线程处于等待状态，唤醒前不再执行后面的代码
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }
                System.out.println("条件不满足，结束：" + Thread.currentThread());
            }
        }
    }

    static class Notify implements Runnable{
        public void run() {
            synchronized (LOCK){
                System.out.println("获取到锁，修改条件：" + Thread.currentThread());
                //LOCK.notify();
                LOCK.notifyAll();
                // notify()或者notifyAll()会再让出时间片之前结束线程所有工作，所以方法后面的代码会被执行。
                flag = false;
            }
        }
    }


}
