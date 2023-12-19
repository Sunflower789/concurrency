import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantLockTest {
    public static void main(String[] args) {
        final AtomicInteger count = new AtomicInteger(0);
        final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                public void run() {
                    reentrantReadWriteLock.writeLock().lock();  // lock会阻塞，tryLock不会阻塞
                    try{
                        TimeUnit.MILLISECONDS.sleep(100);
                        int temp = count.incrementAndGet();     // getAndIncrement返回旧值，incrementAndGet返回新值
                        System.out.println("write count is: " + temp);
                    }catch (Exception ex){

                    }finally {
                        reentrantReadWriteLock.writeLock().unlock();
                    }
                }
            }).start();
        }

        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                public void run() {
                    reentrantReadWriteLock.readLock().lock();  // lock会阻塞，tryLock不会阻塞
                    try{
                        TimeUnit.MILLISECONDS.sleep(100);
                        System.out.println("count is: " + count);
                    }catch (Exception ex){

                    }finally {
                        reentrantReadWriteLock.readLock().unlock();
                    }
                }
            }).start();
        }


    }
}
