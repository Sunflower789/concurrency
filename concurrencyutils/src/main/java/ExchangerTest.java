import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 交换者
 *
 * */
public class ExchangerTest {
    private static final Exchanger<String> exchanger = new Exchanger<String>();
    private static ExecutorService threadPool = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        threadPool.execute(new Runnable() {
            public void run() {
                try{
                    String A = "A";
                    exchanger.exchange(A);
                }catch (InterruptedException interruptedException){

                }
            }
        });

        threadPool.execute(new Runnable() {
            public void run() {
                try{
                    String B = "B";
                    String A = exchanger.exchange(B);
                    System.out.println("A is :" + A + ", B is :" + B + "，A和B是否一致：" + A.equals(B));
                }catch (InterruptedException interruptedException){

                }
            }
        });

        threadPool.shutdown();

    }
}
