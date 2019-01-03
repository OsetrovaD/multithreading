package com.osetrova.model;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class DayChange implements Runnable {

    private final List<RobotDetail> dump;
    private static final long DAY_LENGTH = 100L;
    private int daysNumber;

    @Override
    public void run() {
        int currentDay = 0;
        synchronized (dump) {
            while (currentDay < daysNumber) {
                try {
                    dump.wait(DAY_LENGTH);
                    System.out.println("Наступила ночь");
                    System.out.println("На свалке " + dump);
                    dump.notifyAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                currentDay++;
            }
        }
    }
}
