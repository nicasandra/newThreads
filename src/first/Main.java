package first;

import java.util.concurrent.*;

/**
 * Created by nicasandra on 11/11/2016.
 */

public class Main {
    public static ArrayBlockingQueue<Integer> initializeArray(int capacity) {
        ArrayBlockingQueue<Integer> array = new ArrayBlockingQueue<Integer>(capacity);
        for (int i = 0; i < capacity; i++) {
            array.add(i);
        }
        return array;
    }

    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue<Integer> source = initializeArray(100);

        ArrayBlockingQueue<Integer> destination = new ArrayBlockingQueue(100);

        long start = System.currentTimeMillis();

        int nOfThreads = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(nOfThreads);
        for (int i = 0; i < nOfThreads; i++) {
            executorService.execute(new PrimeNumbers(source, destination));

        }
        executorService.shutdown();

        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        long finish = System.currentTimeMillis();
        System.out.println(finish - start);
        System.out.println(destination);
    }


}
