package org.example.task2;

import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    public static void main(String[] args) {

        // интересная задача, пробовал разные варианты, в результате остановился на том, при котором
        // запускаются одновременно задачи чтения и записи
        // в общем, это целая наука, т.к судя по наблюдениям на результаты влияет вообще все))
        // единственное стабильное в этом деле: чем больше объем массива, тем значительнее преимущество
        // ConcurrentHashMap над synchronizedMap

        Tester tester = new Tester();
        tester.printHeader();
        int tasks = 100; // по 100 каждого типа

        tester.test(new ConcurrentHashMap<>(), 1_000, tasks);
        tester.test(Collections.synchronizedMap(new HashMap<>()), 1_000, tasks);

        tester.test(new ConcurrentHashMap<>(), 1_000_000, tasks);
        tester.test(Collections.synchronizedMap(new HashMap<>()), 1_000_000, tasks);
    }
}
