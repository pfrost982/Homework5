package com.geekbrains;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;

public class Car implements Runnable {
    private static int CARS_COUNT;

    static {
        CARS_COUNT = 0;
    }

    private Race race;
    private int speed;
    private String name;
    private CountDownLatch raceReady;
    private Lock startLock;
    private CountDownLatch raceOver;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed, CountDownLatch raceReady, Lock startLock, CountDownLatch raceOver) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        this.raceReady = raceReady;
        this.startLock = startLock;
        this.raceOver = raceOver;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            raceReady.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            startLock.lock();
        } finally {
            startLock.unlock();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        if (!race.isWinnerExist()) {
            System.out.println(name + " ПОБЕДИТЕЛЬ!!!");
        }
        try {
            raceOver.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
