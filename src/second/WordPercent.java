package second;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by Nick on 11/12/2016.
 */
public class WordPercent implements Runnable {
    ArrayBlockingQueue<String> words;
    Map<String, Integer> counter;

    public WordPercent(ArrayBlockingQueue<String> words, Map<String, Integer> counter) {
        this.words = words;
        this.counter = counter;
    }

    @Override
    public void run() {
        String elem = "";
        while (!(words.isEmpty())) {
            synchronized (words) {
                if (!(words.isEmpty())) {
                    elem = words.poll();
                }
            }
            synchronized (counter) {
                if (!counter.containsKey(elem)) {
                    this.counter.put(elem, 1);
                } else {
                    this.counter.put(elem, this.counter.get(elem) + 1);
                }
            }
        }

    }

    public static void writeInFile(Map<String, Integer> words, String file) {
        RandomAccessFile raf = null;
        StringBuilder sb = new StringBuilder();
        try {
            raf = new RandomAccessFile(file, "rw");
            Integer sum = 0;
            for (Integer elem : words.values()) {
                sum += elem;
            }
            for (Map.Entry<String, Integer> e : words.entrySet()) {
                double percent = e.getValue() * 100.0 / sum;
                sb.append(e.getKey()).append(" ").append(String.format("%.2f", percent)).append("%\n");
            }
            raf.write(sb.toString().getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                raf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Check "+file+"!");
    }
}
