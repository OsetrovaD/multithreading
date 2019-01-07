package com.osetrova.model;

import com.osetrova.util.RandomUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Scientist {

    @Getter
    private Map<RobotDetail, Integer> detailsStore = new EnumMap<>(RobotDetail.class);

    public int makeRobot() {
        RobotDetail[] robotDetails = RobotDetail.values();
        int result = 0;

        if (detailsStore.size() == robotDetails.length) {
            result = detailsStore.values().stream()
                    .min(Comparator.naturalOrder())
                    .orElse(0);
            getDetails(result);
        }

        return result;
    }

    private void getDetails(int number) {
        detailsStore.forEach((k,v) -> detailsStore.merge(k, -number, Integer::sum));
        detailsStore = detailsStore.entrySet().stream()
                .filter(e -> e.getValue() != 0)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @AllArgsConstructor
    public class Helper implements Runnable {

        private static final int MAX_DETAILS_NUMBER = 4;
        private final List<RobotDetail> dump;

        @Override
        public void run() {
            synchronized (dump) {
                while (true) {
                    try {
                        dump.wait();
                        System.out.println(Thread.currentThread().getName() + " пришёл на свалку");
                        if (!dump.isEmpty()) {
                            int number = RandomUtil.generate(MAX_DETAILS_NUMBER) + 1;
                            if (dump.size() > number) {
                                getDetails(number);
                            } else {
                                getDetails(dump.size());
                            }
                            System.out.println(detailsStore);
                        }
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        }

        private void getDetails(int number) {
            for (int i = 0; i < number; i++) {
                int detail = RandomUtil.generate(dump.size());
                RobotDetail robotDetail = dump.remove(detail);
                System.out.println(Thread.currentThread().getName() + " забрал " + robotDetail);
                detailsStore.merge(robotDetail, 1, Integer::sum);
            }
        }
    }
}
