package com.geekbrains;

import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {
    private Semaphore tunnelAccess;

    public Tunnel(int permits) {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
        tunnelAccess = new Semaphore(permits);
    }

    @Override
    public void go(Car c) {
        try {
            try {
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                tunnelAccess.acquire();
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);
                tunnelAccess.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}