package first;

import java.util.stream.IntStream;

/**
 * 问题:多线程运行一定比单线程快吗
 *
 * @author chujun
 * @date 2019-11-29 15:55
 */
public class ConcurrentTest {
    private static final long count = 10000L;

    public static void main(String[] args) throws InterruptedException {
        IntStream.range(0, 5).forEach(i -> {
            int size = (int) Math.pow(10, i);
            try {
                concurrent(count * size);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            serial(count * size);
        });
    }

    private static void concurrent(long count) throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread thread = new Thread(() -> {
            int a = 0;
            for (int i = 0; i < count; i++) {
                a += 5;
            }
        });
        thread.start();
        int b = 0;
        for (int i = 0; i < count; i++) {
            b--;
        }
        long time = System.currentTimeMillis() - start;
        thread.join();
        System.out.println("concurrent: cost " + time + "ms,count=" + count + ",b=" + b);
    }

    private static void serial(long count) {
        long start = System.currentTimeMillis();
        int a = 0;
        for (int i = 0; i < count; i++) {
            a += 5;
        }
        int b = 0;
        for (int i = 0; i < count; i++) {
            b--;
        }
        long time = System.currentTimeMillis() - start;
        System.out.println("serial: cost " + time + "ms,count=" + count + ",b=" + b + ",a=" + a);
    }
}
/**
 * 执行结果
 * concurrent: cost 2ms,count=10000,b=-10000
 * serial: cost 1ms,count=10000,b=-10000,a=50000
 * concurrent: cost 2ms,count=100000,b=-100000
 * serial: cost 3ms,count=100000,b=-100000,a=500000
 * concurrent: cost 5ms,count=1000000,b=-1000000
 * serial: cost 9ms,count=1000000,b=-1000000,a=5000000
 * concurrent: cost 14ms,count=10000000,b=-10000000
 * serial: cost 12ms,count=10000000,b=-10000000,a=50000000
 * concurrent: cost 157ms,count=100000000,b=-100000000
 * serial: cost 98ms,count=100000000,b=-100000000,a=500000000
 */
