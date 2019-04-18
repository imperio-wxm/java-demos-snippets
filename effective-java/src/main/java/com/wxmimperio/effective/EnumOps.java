package com.wxmimperio.effective;

import java.util.*;

public class EnumOps {

    public static void main(String[] args) {
        // part 1
        System.out.println("plus " + Operation.PLUS.reduce(1, 2));
        System.out.println("minus " + Operation.MINUS.reduce(1, 2));
        System.out.println("times " + Operation.TIMES.reduce(1, 2));
        System.out.println("divide " + Operation.DIVIDE.reduce(1, 2));

        // part 2
        Arrays.asList(Operation2.values()).forEach(ops -> System.out.println(ops.symbol + "," + ops.reduce(4, 5)));

        // part 3
        Map<Storage.Type, List<Storage>> typeSetMap = new EnumMap<>(Storage.Type.class);
        // 为每个type 初始化一个list
        Arrays.asList(Storage.Type.values()).forEach(type -> typeSetMap.put(type, new ArrayList<>()));
        Storage[] storages = {
                new Storage(Storage.Type.ES, "es01"),
                new Storage(Storage.Type.ES, "es02"),
                new Storage(Storage.Type.HBASE, "hbase01"),
                new Storage(Storage.Type.HBASE, "hbase02"),
                new Storage(Storage.Type.HIVE, "hive02"),
        };
        // 遍历Storage，按类型将实体存入
        Arrays.asList(storages).forEach(storage -> typeSetMap.get(storage.type).add(storage));
        typeSetMap.forEach((key, value) -> System.out.println(String.format("key = %s, value = %s", key, value)));
    }

    public enum Operation {
        /**
         * 加法
         */
        PLUS {
            @Override
            double reduce(double x, double y) {
                return x + y;
            }
        },
        MINUS {
            @Override
            double reduce(double x, double y) {
                return x - y;
            }
        },
        TIMES {
            @Override
            double reduce(double x, double y) {
                return x * y;
            }
        },
        DIVIDE {
            @Override
            double reduce(double x, double y) {
                return x / y;
            }
        };

        abstract double reduce(double x, double y);
    }

    public enum Operation2 {
        /**
         * 加法
         */
        PLUS("+") {
            @Override
            double reduce(double x, double y) {
                return x + y;
            }
        },
        MINUS("-") {
            @Override
            double reduce(double x, double y) {
                return x - y;
            }
        },
        TIMES("*") {
            @Override
            double reduce(double x, double y) {
                return x * y;
            }
        },
        DIVIDE("/") {
            @Override
            double reduce(double x, double y) {
                return x / y;
            }
        };

        private final String symbol;

        Operation2(String symbol) {
            this.symbol = symbol;
        }

        abstract double reduce(double x, double y);

        @Override
        public String toString() {
            return "Operation2{" +
                    "symbol='" + symbol + '\'' +
                    '}';
        }
    }

    public static class Storage {

        public enum Type {
            /***
             * type for storage
             */
            HIVE, ES, CASSANDRA, PHOENIX, HBASE
        }

        private Type type;
        private String name;

        public Storage(Type type, String name) {
            this.type = type;
            this.name = name;
        }

        @Override
        public String toString() {
            return "Storage{" +
                    "type=" + type +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
