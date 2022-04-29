package ru.shemich.tasks.igotyou;

import java.util.concurrent.BlockingQueue;

import static ru.shemich.tasks.igotyou.SharedObject.lyrics;
/*
    Перебираем массив lyrics, блокирующая очередь «первым прибыл - первым убыл», ищем "Cher" или "Cher, Sonny" и выводим
*/
public class Cher extends Thread {

    BlockingQueue<String> in;
    BlockingQueue<String> out;

    public Cher(BlockingQueue<String> in, BlockingQueue<String> out){
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("Cher");
        for (int i = 0; i < lyrics.length; i++){
            try {
                in.take();
                if ("Cher".equals(lyrics[i][0])) {
                    sleep(1000);
                    System.out.println(Thread.currentThread().getName() + ": " + lyrics[i][1]);
                } else if ("Sonny, Cher".equals(lyrics[i][0])) {
                    sleep(1000);
                    System.out.println(Thread.currentThread().getName() + ": " + lyrics[i][1]);
                }
                out.put("TURN");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}