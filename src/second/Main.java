package second;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Nick on 11/12/2016.
 */
public class Main {

    public static ArrayBlockingQueue<String> initializeArray(String source) {
        ArrayList<String> list = new ArrayList<>();
        String[] lines = source.split("\n");
        for (int i = 0; i < lines.length; i++) {
            String[] words = lines[i].split(" ");
            for (int j = 0; j < words.length; j++) {
                list.add(words[j]);
            }
        }
        ArrayBlockingQueue<String> array = new ArrayBlockingQueue<>(list.size());

        for (String e : list) {
            array.add(e);
        }
        return array;
    }

    public static String readFromFile(String file) {
        StringBuilder sb = new StringBuilder();
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(file, "rw");
            String line = "";
            while ((line = raf.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
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
        return null;
    }

    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue<String> words = initializeArray(readFromFile("source.in"));
        Map<String, Integer> map = new HashMap<>();

        int nOfThreads = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(nOfThreads);
        for (int i = 0; i < nOfThreads; i++) {
            executorService.execute(new WordPercent(words, map));
        }
        executorService.shutdown();

        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        /*
        Write in file
         */
        WordPercent.writeInFile(map, "destination.in");


    }
}
