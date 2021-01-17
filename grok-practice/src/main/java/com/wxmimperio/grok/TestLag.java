package com.wxmimperio.grok;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TestLag {

    public static void main(String[] args) {
        List<Data> data = getData();

        data = data.stream().sorted(new Comparator<Data>() {
            @Override
            public int compare(Data o1, Data o2) {
                return Long.valueOf(o1.getTimestamp().getTime() - o2.getTimestamp().getTime()).intValue();
            }
        }).collect(Collectors.toList());

       /* List<Double> diff = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            if (i == data.size() - 1) {
                continue;
            }
            Data d1 = data.get(i);
            Data d2 = data.get(i + 1);
            diff.add(diff(d1, d2));
        }
        System.out.println(data);
        System.out.println(diff);

        List<Double> increase = diff.stream().filter(aDouble -> aDouble >= 0).collect(Collectors.toList());
        List<Double> decrease = diff.stream().filter(aDouble -> aDouble <= 0).collect(Collectors.toList());*/


        System.out.println(data);

        if (isIncrease(data)) {
            System.out.println("上升");
        } else if (isDecrease(data)) {
            System.out.println("下降");
        } else {
            System.out.println("波动");
        }

        System.out.println(isIncrease(data));
    }

    private static boolean isIncrease(List<Data> data) {
        for (int i = 0; i < data.size(); i++) {
            if (i < data.size() - 1) {
                if (data.get(i).getValue() >= data.get(i + 1).getValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isDecrease(List<Data> data) {
        for (int i = 0; i < data.size(); i++) {
            if (i < data.size() - 1) {
                if (data.get(i).getValue() <= data.get(i + 1).getValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    private static double diff(Data d1, Data d2) {
        return d1.getValue() - d2.getValue();
    }

    private static List<Data> getData() {
        List<Data> data = new ArrayList<>();
        data.add(new Data(new Timestamp(System.currentTimeMillis()), 1.5));
        data.add(new Data(new Timestamp(System.currentTimeMillis() - 60 * 1000 * 5L), 1.4));
        data.add(new Data(new Timestamp(System.currentTimeMillis() - 60 * 1000 * 10L), 0.9));
        data.add(new Data(new Timestamp(System.currentTimeMillis() - 60 * 1000 * 15L), 0.8));
        data.add(new Data(new Timestamp(System.currentTimeMillis() - 60 * 1000 * 20L), 0.7));
        data.add(new Data(new Timestamp(System.currentTimeMillis() - 60 * 1000 * 25L), 0.6));
        data.add(new Data(new Timestamp(System.currentTimeMillis() - 60 * 1000 * 30L), 0.5));
        return data;
    }

    private static class Data {
        private Timestamp timestamp;
        private Double value;

        public Data(Timestamp timestamp, Double value) {
            this.timestamp = timestamp;
            this.value = value;
        }

        public Timestamp getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Timestamp timestamp) {
            this.timestamp = timestamp;
        }

        public Double getValue() {
            return value;
        }

        public void setValue(Double value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "timestamp=" + timestamp +
                    ", value=" + value +
                    '}';
        }
    }
}
