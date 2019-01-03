package com.osetrova.model;

import com.osetrova.testutil.DumpUtil;
import org.junit.Test;

import java.util.List;

public class ScientistsCompetitionTest {

    @Test
    public void competition() {
        List<RobotDetail> dump = DumpUtil.fillDump();

        Scientist firstScientist = new Scientist();
        Thread firstScientistHelper = new Thread(firstScientist.new Helper(dump));

        Scientist secondScientist = new Scientist();
        Thread secondScientistHelper = new Thread(secondScientist.new Helper(dump));

        Thread factory = new Thread(new Factory(dump));
        Thread dayChange = new Thread(new DayChange(dump, 100));

        dayChange.start();
        factory.start();
        firstScientistHelper.start();
        secondScientistHelper.start();

        try {
            dayChange.join();
            if (!dayChange.isAlive()) {
                firstScientistHelper.interrupt();
                secondScientistHelper.interrupt();
                factory.interrupt();
            }

            firstScientistHelper.join();
            secondScientistHelper.join();
            factory.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println(firstScientist.makeRobot());
        System.out.println(secondScientist.makeRobot());
    }
}
