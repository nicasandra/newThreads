package second;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nick on 11/12/2016.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        List<String> words = Arrays.asList("ana", "ana", "are", "mere",
                "are", "ana", "mere", "ana", "ana", "are", "mere",
                "are", "ana", "mere", "ana", "ana", "are", "mere",
                "are", "ana", "mere", "ana", "ana", "are", "mere",
                "are", "ana", "mere", "are");
        Map<String, Integer> map = new HashMap<>();

        WordPercent wp = new WordPercent(words, map);
        Thread t = new Thread(wp);

        long start1 = System.currentTimeMillis();
        t.start();
        t.join();

        long finish1 = System.currentTimeMillis();
        for (Map.Entry<String, Integer> e : map.entrySet()) {
            System.out.println(e.getKey() + " - " + e.getValue());
        }

        System.out.println("1 thread time: " + (finish1 - start1) + "\n");
        map = new HashMap<>();
        WordPercent wp1 = new WordPercent(words.subList(0, 10), map);
        WordPercent wp2 = new WordPercent(words.subList(10, 20), map);
        WordPercent wp3 = new WordPercent(words.subList(20, words.size()), map);

        Thread t1 = new Thread(wp1);
        Thread t2 = new Thread(wp2);
        Thread t3 = new Thread(wp3);

        long start2 = System.currentTimeMillis();

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        long finish2 = System.currentTimeMillis();

        for (Map.Entry<String, Integer> e : map.entrySet()) {
            System.out.println(e.getKey() + " - " + e.getValue());
        }

        System.out.println("3 threads time: " + (finish2 - start2));

        /*
        Write in file
         */
        WordPercent.writeInFile(map, "destination.in");


    }
}
