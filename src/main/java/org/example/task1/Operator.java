package org.example.task1;

import java.util.concurrent.TimeUnit;

public class Operator implements Runnable {

    private static final long PROCESSING_TIME = 3000;

    private final Exchange exchange;

    public Operator(Exchange exchange) {
        this.exchange = exchange;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Caller caller = exchange.process();
                if (caller != null) {
                    TimeUnit.MILLISECONDS.sleep(PROCESSING_TIME);
                    System.out.println("Звонок от " + caller + " обработан!");
                    caller.serve();
                } else {
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
