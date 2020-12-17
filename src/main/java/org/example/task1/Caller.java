package org.example.task1;

import java.util.concurrent.CountDownLatch;

public class Caller implements Runnable {

    private static int counter = 1;

    private final String name;
    private final Exchange exchange;
    private final CountDownLatch latch = new CountDownLatch(1);

    private boolean queued = false;
    private boolean served = false;

    public Caller(Exchange exchange) {
        this.name = "Пользователь " + counter++;
        this.exchange = exchange;
    }

    @Override
    public void run() {
        while (!queued) {
            queued = exchange.submit(this);
            if (!queued) {
                System.out.println(name + ": Попробую еще раз позвонить!");
            }
        }
        while (!served) {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(name + ": Ура!");
    }

    public void serve() {
        this.latch.countDown();
        this.served = true;
    }

    @Override
    public String toString() {
        return name;
    }
}
