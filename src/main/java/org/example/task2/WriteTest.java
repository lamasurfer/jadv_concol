package org.example.task2;

import java.util.Map;
import java.util.concurrent.atomic.DoubleAdder;

public class WriteTest implements Runnable {

    private final Map<Integer, Integer> map;
    private final DoubleAdder writeAdder;
    private final int[] array;


    public WriteTest(Map<Integer, Integer> map, DoubleAdder writeAdder, int[] array) {
        this.map = map;
        this.writeAdder = writeAdder;
        this.array = array;
    }

    @Override
    public void run() {

        long startTime = System.nanoTime();
        for (int i = 0; i < array.length; i++) {
            map.put(i, array[i]);
        }
        long endTime = System.nanoTime() - startTime;
        double writeTime = endTime / 1_000_000_000.0;
        writeAdder.add(writeTime);

    }
}
