package com.eakurnikov.autoque;

import java.util.ArrayList;
import java.util.List;

public class Duplicates {

    public static void main(String... args) {
        int[] a1 = {};
        int[] a2 = {0};
        int[] a3 = {0, 0, 0, 0, 1, 1, 1, 1};
        int[] a3handled = {0, 1};
        int[] a4 = {2, 2, 2, 2, 2, 2, 2};
        int[] a4handled = {2};
        int[] a5 = {0, 0, 1, 2, 2, 3, 4, 5, 6, 6, 6, 6};
        int[] a5handled = {0, 1, 2, 3, 4, 5, 6};

//        System.out.println(areArraysEqual(a1, removeDuplicates(a1)));
//        System.out.println(areArraysEqual(a2, removeDuplicates(a2)));
//        System.out.println(areArraysEqual(a3handled, removeDuplicates(a3)));
//        System.out.println(areArraysEqual(a4handled, removeDuplicates(a4)));
//        System.out.println(areArraysEqual(a5handled, removeDuplicates(a5)));

        removeInMemoryAndPrint(a1);
        removeInMemoryAndPrint(a2);
        removeInMemoryAndPrint(a3);
        removeInMemoryAndPrint(a4);
        removeInMemoryAndPrint(a5);
    }

    private static void removeInMemoryAndPrint(int[] a) {
        if (a.length == 0) {
            System.out.println("empty");
            return;
        }

        System.out.print(a[0]);

        if (a.length == 1) {
            System.out.println();
            return;
        }

        for (int i = 1; i < a.length; i++) {
            if (a[i] != a[i - 1]) {
                System.out.print(a[i]);
            }
        }

        System.out.println();
    }

    private static int[] removeDuplicates(int[] a) {
        if (a.length < 2) {
            return a;
        }
        List<Integer> res = new ArrayList<>();
        res.add(a[0]);

        for (int i = 1; i < a.length; i++) {
            if (a[i] != a[i - 1]) {
                res.add(a[i]);
            }
        }

        int[] result = new int[res.size()];
        for (int i = 0; i < res.size(); i++) {
            result[i] = res.get(i);
        }

        return result;
    }

    private static boolean areArraysEqual(int[] a, int[] b) {
        if (a == b) return true;
        if (a.length != b.length) return false;
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }
}