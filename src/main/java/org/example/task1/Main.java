package org.example.task1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {

        ExecutorService executor = Executors.newCachedThreadPool();

        Exchange exchange = new Exchange();
        exchange.start(executor, 30);

        Callers callers = new Callers(exchange, executor);
        callers.start(5, 60);

        executor.shutdown();

    }
}
