package com.osetrova.model;

import com.osetrova.util.RandomUtil;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class Factory implements Runnable {

    private final List<RobotDetail> dump;
    private static final int MAX_DETAILS_NUMBER = 4;

    @Override
    public void run() {
        RobotDetail[] robotDetails = RobotDetail.values();
        synchronized (dump) {
            while (true) {
                try {
                    dump.wait();
                    int number = RandomUtil.generate(MAX_DETAILS_NUMBER) + 1;

                    for (int i = 0; i < number; i++) {
                        int detail = RandomUtil.generate(robotDetails.length);
                        RobotDetail robotDetail = robotDetails[detail];
                        System.out.println(Thread.currentThread().getName() + " произвёл и выкинул " + robotDetail);
                        dump.add(robotDetail);
                    }
                    System.out.println("На свалке " + dump);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }
}
