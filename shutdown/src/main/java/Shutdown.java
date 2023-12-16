import java.util.concurrent.TimeUnit;

/**
 * 使用中断结束线程
 * 或者  使用boolean变量结束线程
 * */
public class Shutdown {
    public static void main(String[] args) throws InterruptedException {
        Runner runner = new Runner();
        Thread thread = new Thread(runner,"shutdown");
        thread.start();
        TimeUnit.SECONDS.sleep(1);

        thread.interrupt();
        runner.cancel();
    }

    private static class Runner implements Runnable{
        private volatile boolean on = true;
        private long i;

        public void run() {
            while(!Thread.currentThread().isInterrupted() && on){
                i++;
            }
            System.out.println("count i:" + i);
        }

        public void cancel(){
            on = false;
        }
    }
}
