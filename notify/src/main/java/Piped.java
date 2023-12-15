import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 *
 * 利用管道实现线程间通信
 * 管道主要用于线程间的数据传输，而传输媒介依然是内存
 *
 * PipedOutputStream和PipedInputStream面向字节的
 * PipedWriter和PipedReader是面向字符的
 *
 * */
public class Piped {
    public static void main(String[] args) throws IOException {
        PipedWriter out = new PipedWriter();
        PipedReader in = new PipedReader();
        // 将输出流和输入流进行连接
        out.connect(in);

        Thread printThread = new Thread(new Print(in), "PrintThread");
        printThread.start();

        int receive = 0;
        try{
            while ((receive = System.in.read()) != -1){
                out.write(receive);
            }
        }catch (IOException ex){
            out.close();
        }

    }

    static class Print implements Runnable{
        private PipedReader in;

        public Print(PipedReader in) {
            this.in = in;
        }

        public void run() {
            int receive = 0;
            try{
                while ((receive = in.read()) != -1){
                    System.out.println((char) receive);
                }
            }catch (IOException ex){

            }
        }
    }
}
