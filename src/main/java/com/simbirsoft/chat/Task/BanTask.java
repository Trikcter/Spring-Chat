package com.simbirsoft.chat.Task;

import java.util.Date;

public class BanTask implements Runnable {
    private String message;

    public BanTask(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        System.out.println(new Date() + " Runnable Task with " + message
                + " on thread " + Thread.currentThread().getName());
    }
}
