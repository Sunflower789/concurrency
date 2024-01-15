import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class FutureTaskTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> task = new FutureTask<>(()->{
            TimeUnit.SECONDS.sleep(1);
            return "success";
        });

        new Thread(task).start();
        System.out.println("result: " + task.get());
    }
}
