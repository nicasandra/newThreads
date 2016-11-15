package first;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by nicasandra on 11/11/2016.
 */
public class PrimeNumbers implements Runnable {
    private ArrayBlockingQueue<Integer> source;
    private ArrayBlockingQueue<Integer> destination;

    public PrimeNumbers(ArrayBlockingQueue<Integer> source, ArrayBlockingQueue<Integer> destination) {
        this.source = source;
        this.destination = destination;
    }

    @Override
    public void run() {
        int number = 0;
        while (!(source.isEmpty())) {
            synchronized (source) {
                if (!source.isEmpty()) {
                    number = source.poll();
                }
            }
            if (isPrime(number)) {
                synchronized (destination) {
                    destination.add(number);
                }
            }
        }
    }

    boolean isPrime(int n) {
        for (int i = 2; i < n; i++) {
            if (n % i == 0)
                return false;
        }
        return true;
    }
}
