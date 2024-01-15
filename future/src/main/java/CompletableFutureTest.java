import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

public class CompletableFutureTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 5, 3L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(5),
                new ThreadFactoryBuilder().setNameFormat("Thread-task-%d").build(),
                new ThreadPoolExecutor.AbortPolicy());


        // supplyAsync是带有返回值的，runAsync是无返回值的。
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            return "success";
        },threadPoolExecutor)
                .whenCompleteAsync((result,exception)->{                 //whenCompleteAsync上面方法执行完毕后才执行  thenRunAsync/thenAcceptAsync串行执行，后者可以拿到前一个任务的返回结果
                    System.out.println("result is : " + result);
                    System.out.println("exception is : " + exception);
                    System.out.println("执行完毕");
                },threadPoolExecutor)
                .exceptionally((exception)->{                            //异常处理
                    System.out.println("handler exception is : " + exception);
                    return "fail";
                });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            return "success";
        },threadPoolExecutor)
                .handle((result,exception)->{                             //处理回调
                    System.out.println("result is : " + result);
                    System.out.println("执行完毕");
                    if(exception == null){
                        return result;
                    }else{
                        System.out.println("exception is : " + exception);
                        return "fail";
                    }
                });

        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            return "success";
        },threadPoolExecutor)
                .handle((result,exception)->{                             //处理回调
                    System.out.println("result is : " + result);
                    System.out.println("执行完毕");
                    if(exception == null){
                        return result;
                    }else{
                        System.out.println("exception is : " + exception);
                        return "fail";
                    }
                });

        // 两个都执行完毕
//        CompletableFuture<Void> futureBoth = future1.runAfterBoth(future2,()->{
//            System.out.println("两个都执行完毕");
//        });
//        CompletableFuture<Void> futureBoth = future1.thenAcceptBothAsync(future2, (f1, f2) -> {
//            System.out.println("f1 result is: " + f1 + ",f1 result is: " + f2);
//        }, threadPoolExecutor);
//        CompletableFuture<String> futureBoth = future1.thenCombineAsync(future2, (f1, f2) -> {
//            System.out.println("f1 result is: " + f1 + ",f1 result is: " + f2);
//            return "both success";
//        }, threadPoolExecutor);
//        futureBoth.get();

        // 两个中任一一个执行完毕
//        CompletableFuture<Void> futureEither = future1.runAfterEither(future2, () -> {
//            System.out.println("其中一个执行完毕");
//        });
//        CompletableFuture<String> futureEither = future1.applyToEitherAsync(future2, (result) -> {
//            System.out.println("the either result is : " + result);
//            return "either success";
//        }, threadPoolExecutor);
//        futureEither.get();

        // 多任务同时完成
//        CompletableFuture<Void> futureAll= CompletableFuture.allOf(future1, future2, future3);
//        futureAll.get();
//        System.out.println("f1 is: " + future1.get() + ",f2 is: " + future2.get() + ",f3 is: " + future3.get());

        // 多任务任一一个完成
        CompletableFuture<Object> futureAny= CompletableFuture.anyOf(future1, future2, future3);
        futureAny.get();
        System.out.println("futureAny is: " + futureAny.get());


        System.out.println("main thread end!");
    }
}
