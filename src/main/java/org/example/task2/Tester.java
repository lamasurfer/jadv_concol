package org.example.task2;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.DoubleAdder;

public class Tester {
    public void test(Map<Integer, Integer> map, int arraySize, int tasks) {

        DoubleAdder writeAdder = new DoubleAdder();
        DoubleAdder readAdder = new DoubleAdder();

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        int[] array = getArray(arraySize);
        for (int i = 0; i < tasks; i++) {
            executor.execute(new WriteTest(map, writeAdder, array));
            executor.execute(new ReadTest(map, readAdder, arraySize));
        }
        executor.shutdown();
        try {
            if (!executor.awaitTermination(120, TimeUnit.MINUTES)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }

        double writeTime = writeAdder.sum() / tasks;
        double readTime = readAdder.sum() / tasks;

        String mapName = map.getClass().getSimpleName();
        System.out.printf("%-20s %-7s %-10s %-12.7f %-12.7f%n",
                mapName, tasks, arraySize, writeTime, readTime);

    }

    public void printHeader() {
        System.out.println("Тип map              " +
                "задачи  " +
                "элементы   " +
                "запись, с.   " +
                "чтение, с.");
    }

    public int[] getArray(int arraySize) {
        int[] array = new int[arraySize];
        for (int j = 0; j < array.length; j++) {
            array[j] = ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE);
        }
        return array;
    }

    public static void main(String[] args) {

        final int[] ARRAY_SIZES = {
                10,
                25, 50, 75,
                100, 250, 500, 750,
                1_000, 2_500, 5_000, 7_500,
                10_000, 25_000, 50_000, 75_000,
                100_000, 250_000, 500_000, 750_000,
                1_000_000, 2_500_000, 5_000_000, 7_500_000,
                10_000_000 // пробовал больше, тенденция сохраняется
        };

        Tester tester = new Tester();
        tester.printHeader();

        int tasks = 10;
        for (int array_size : ARRAY_SIZES) {
            tester.test(new ConcurrentHashMap<>(), array_size, tasks);
            tester.test(Collections.synchronizedMap(new HashMap<>()), array_size, tasks);
        }
    }
}
