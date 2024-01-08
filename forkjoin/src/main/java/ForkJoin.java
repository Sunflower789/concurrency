import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 例子：1-100相加的值
 * */
public class ForkJoin {

    // 定义一个工作窃取线程，可以使用默认的参数：CPU核数
    public final static ForkJoinPool mainPool = new ForkJoinPool();
    public final static int NUM = 100;
    public static volatile AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) {
        int[] a = new int[NUM];
        System.out.println("before:");
        for (int i = 1; i <= NUM; i++) {
            a[i-1] = i;
        }
        SubTask task = new SubTask(a, 0, NUM);
        mainPool.invoke(task);
        System.out.println("count: " + count);
    }

    static class SubTask extends RecursiveAction {

        private static final long serialVersionUID = 1L;

        private final int[] a;
        private final int beg;
        private final int end;

        public SubTask(int[] a, int beg, int end) {
            super();
            this.a = a;
            this.beg = beg;
            this.end = end;
        }

        @Override
        protected void compute() {
            if (end - beg > 10) {
                int mid = (beg + end) / 2;
                SubTask t1 = new SubTask(a, beg, mid);
                SubTask t2 = new SubTask(a, mid, end);
                invokeAll(t1, t2);
            } else {
                for (int i = beg; i < end; i++) {
                    int tmp = a[i];
                    count.addAndGet(tmp);
                }
            }
        }
    }


}
