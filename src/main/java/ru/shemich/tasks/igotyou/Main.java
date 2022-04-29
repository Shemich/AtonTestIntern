package ru.shemich.tasks.igotyou;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> t1t2 = new LinkedBlockingQueue<>();
        BlockingQueue<String> t2t1 = new LinkedBlockingQueue<>();
        System.out.println("Начали петь...");
        Cher cherThread = new Cher(t1t2,t2t1);
        Sonny sonnyThread = new Sonny(t2t1,t1t2);
        cherThread.start();
        sonnyThread.start();
        t1t2.put("START");
    }
}

