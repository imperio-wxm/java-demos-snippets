package com.wxmimperio.java8.demo1;

import java.util.*;

/**
 * Created by wxmimperio on 2017/7/16.
 */
public class Java8Compare {
    public static void main(String[] args) {
        List<String> names1 = new ArrayList<String>();
        names1.add("Mahesh ");
        names1.add("Suresh ");
        names1.add("Ramesh ");
        names1.add("Naresh ");
        names1.add("Kalpesh ");

        List<String> names2 = new ArrayList<String>();
        names2.add("Mahesh ");
        names2.add("Suresh ");
        names2.add("Ramesh ");
        names2.add("Naresh ");
        names2.add("Kalpesh ");

        Java8Compare tester = new Java8Compare();

        System.out.println("Sort using Java 7 syntax: ");
        tester.sortUsingJava7(names1);
        System.out.println(names1);

        System.out.println("Sort using Java 8 syntax: ");
        tester.sortUsingJava8(names2);
        System.out.println(names2);
    }

    private void sortUsingJava7(List<String> names) {
        //sort using java 7
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareTo(s2);
            }
        });
    }

    private void sortUsingJava8(List<String> names) {
        //sort using java 8
        //Collections.sort(names, (s1, s2) ->  s1.compareTo(s2));
        Collections.sort(names, Comparator.naturalOrder());
    }

    private void compareWithJava8() {
        //Comparator<Integer> comparator = Integer::compare;
        //Comparator<Integer> comparator = Comparator.comparingInt(x -> x);
        Comparator<Integer> comparator = (x, y) -> Integer.compare(x, y);
        TreeSet<Integer> hashSet = new TreeSet<Integer>(comparator);
    }
}
