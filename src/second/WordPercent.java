package second;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nick on 11/12/2016.
 */
public class WordPercent implements Runnable {
    List<String> words;
    Map<String, Integer> counter;

    public WordPercent() {
    }

    public WordPercent(List<String> words, Map<String, Integer> counter) {

        this.words = words;
        this.counter = counter;
    }

    public List<String> getWords() {

        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    public Map<String, Integer> getCounter() {
        return counter;
    }

    public void setCounter(Map<String, Integer> counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (String elem : words) {
            synchronized (counter) {
                if (!counter.containsKey(elem)) {
                    this.counter.put(elem, 1);
                } else {
                    synchronized (counter) {
                        this.counter.put(elem, this.counter.get(elem) + 1);
                    }
                }
            }
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
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
    }
}
