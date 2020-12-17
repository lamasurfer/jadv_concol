package org.example.task1;

import java.util.concurrent.ExecutorService;

public class Callers {

    private final Exchange exchange;
    private final ExecutorService executor;

    public Callers(Exchange exchange, ExecutorService executor) {
        this.exchange = exchange;
        this.executor = executor;
    }

    public void start(int waves, int callers) {
        for (int i = 0; i < waves; i++) {
            try {
                for (int j = 0; j < callers; j++) {
                    executor.execute(new Caller(exchange));
                    Thread.sleep(10);
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
