package com.osetrova.testutil;

import com.osetrova.model.RobotDetail;
import com.osetrova.util.RandomUtil;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class DumpUtil {

    private static final int MAX_NUMBER = 20;

    public static List<RobotDetail> fillDump() {
        List<RobotDetail> details = new ArrayList<>();
        RobotDetail[] robotDetails = RobotDetail.values();

        for (int i = 0; i < MAX_NUMBER; i++) {
            int detail = RandomUtil.generate(robotDetails.length);
            details.add(robotDetails[detail]);
        }

        return details;
    }
}
