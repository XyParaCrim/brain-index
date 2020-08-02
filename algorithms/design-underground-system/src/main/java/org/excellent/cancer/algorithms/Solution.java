package org.excellent.cancer.algorithms;

import java.util.HashMap;

public class Solution implements DesignUndergroundSystem {


    private final HashMap<Integer, Record> recordMap = new HashMap<>();

    private final HashMap<Integer, Statistics> statisticsMap = new HashMap<>();


    @Override
    public void checkIn(int id, String stationName, int t) {
        recordMap.put(id, new Record(stationName, t));
    }

    @Override
    public void checkOut(int id, String stationName, int t) {
        Record record = recordMap.remove(id);
        statisticsMap.computeIfAbsent(statisticsKey(record.source, stationName), (i) -> new Statistics()).
                add(record, t);

    }

    @Override
    public double getAverageTime(String startStation, String endStation) {
        return statisticsMap.get(statisticsKey(startStation, endStation)).average();
    }

    private static class Record {

        String source;

        int start;

        public Record(String source, int start) {
            this.source = source;
            this.start = start;
        }
    }

    private static int statisticsKey(String source, String target) {
        int sourceCode = source.hashCode();
        int targetCode = target.hashCode();

        return sourceCode > targetCode ? sourceCode + targetCode * 31 : targetCode + sourceCode * 31;
    }

    private static class Statistics {

        double time = 0;

        int count = 0;

        void add(Record record, int end) {
            time += end - record.start;
            ++count;
        }

        double average() {
            return time / count;
        }

    }
}
