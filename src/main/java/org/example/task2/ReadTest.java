package org.example.task2;

import java.util.Map;
import java.util.concurrent.atomic.DoubleAdder;

public class ReadTest implements Runnable {

    private final Map<Integer, Integer> map;
    private final DoubleAdder readAdder;
    private final int arraySize;


    public ReadTest(Map<Integer, Integer> map, DoubleAdder readAdder, int arraySize) {
        this.map = map;
        this.readAdder = readAdder;
        this.arraySize = arraySize;
    }

    @Override
    public void run() {

        long startTime = System.nanoTime();
        for (int i = 0; i < arraySize; i++) {
            int value = map.getOrDefault(i, 0);
        }
        long endTime = System.nanoTime() - startTime;
        double readTime = endTime / 1_000_000_000.0;
        readAdder.add(readTime);

    }
}
