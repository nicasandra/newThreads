package first;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nicasandra on 11/11/2016.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        List<Integer> source = Arrays.asList(2, 4, 6, 8, 10, 14, 17, 19, 24, 56, 78, 89, 45, 12,
                45, 345, 34, 2, 4, 6, 8, 10, 14, 17, 19, 24, 56, 78, 89, 45, 12, 45,
                345, 34, 2, 4, 6, 8, 10, 14, 17, 19, 24, 56, 78, 89, 45, 12, 45, 89);
        List<Integer> firstDest = new ArrayList<>();
        List<Integer> secondDest = new ArrayList<>();
        List<Integer> sub1 = source.subList(0, 20);
        List<Integer> sub2 = source.subList(21, 33);
        List<Integer> sub3 = source.subList(34, source.size());

        PrimeNumbers p1 = new PrimeNumbers(sub1, firstDest);
        PrimeNumbers p2 = new PrimeNumbers(sub2, firstDest);
        PrimeNumbers p3 = new PrimeNumbers(sub3, firstDest);
        PrimeNumbers p4 = new PrimeNumbers(source, secondDest);
        Thread t1 = new Thread(p1);
        Thread t2 = new Thread(p2);
        Thread t3 = new Thread(p3);
        Thread t4 = new Thread(p4);

        long startTime = System.currentTimeMillis();

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();


        long finishTime = System.currentTimeMillis();
        System.out.println("\n\nThree threads time " + (finishTime - startTime) + "\n\n");

        long start = System.currentTimeMillis();
        t4.start();
        t4.join();

        long finish = System.currentTimeMillis();
        System.out.println("\n\nSingle thread time: " + (finish - start));
    }


}
