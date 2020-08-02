package org.excellent.cancer.algorithms;

public interface DesignUndergroundSystem {

    void checkIn(int id, String stationName, int t);

    void checkOut(int id, String stationName, int t);

    double getAverageTime(String startStation, String endStation);

    static DesignUndergroundSystem solution() {
        return new Solution();
    }
}