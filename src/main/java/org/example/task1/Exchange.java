package org.example.task1;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Exchange {

    private final static long WAITING_TIME = 10_000;
    private final static long IDLE_TIME = 3_000;

    // выбрал очередь с возможностью ограничения, чтобы реализовать колл-центр "как раньше",
    // когда звонишь - висишь десять минут - гудки и ты звонишь еще раз))
    private final BlockingQueue<Caller> callers = new LinkedBlockingQueue<>(60);

    public boolean submit(Caller caller) {
        try {
            if (callers.offer(caller, WAITING_TIME, TimeUnit.MILLISECONDS)) {
                return true;
            } else {
                System.out.println(">> Извините " + caller + ", линия перегружена, перезвоните позже!");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Caller process() {
        Caller caller = null;
        try {
            caller = callers.poll(IDLE_TIME, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return caller;
    }

    public void start(ExecutorService executor, int operators) {
        for (int i = 0; i < operators; i++) {
            executor.execute(new Operator(this));
        }
    }
}
