package first;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadFactory;

/**
 * Created by nicasandra on 11/11/2016.
 */
public class PrimeNumbers implements Runnable {
    List<Integer> source;
    List<Integer> destination;

    public List<Integer> getSource() {
        return source;
    }

    public void setSource(List<Integer> source) {
        this.source = source;
    }

    public List<Integer> getDestination() {
        return destination;
    }

    public void setDestination(List<Integer> destination) {
        this.destination = destination;
    }

    public PrimeNumbers(List<Integer> source, List<Integer> destination) {
        this.source = source;
        this.destination = destination;
    }

    public PrimeNumbers() {
    }

    @Override
    public void run() {
        for (Integer elem : source) {
            if (isPrime(elem)) {
                synchronized (destination) {
                    destination.add(elem);
                    System.out.println(Thread.currentThread().getName() + "   " + elem);
                }
            }
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
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
