package com.eakurnikov.autoque;

public class Merge {

    public static void main(String... args) {
        int[] a1 = {0, 1, 1, 2, 3, 4, 5, 6, 7};
        int[] a2 = {0, 0, 0, 1, 3, 7, 8, 8};
        int[] a3 = {0};
        int[] a4 = {5, 7, 8, 8, 9, 10, 11};
        int[] a5 = {7};
        int[] a6 = {9, 10, 11, 11, 11};
        int[] a7 = {};
        int[] a8 = {1};
        int[] a9 = {0, 0, 0};
        int[] a10 = {1, 1, 1};

        int[] expected = {
                0, 0, 0, 0, 0, 0, 0, 0,
                1, 1, 1, 1, 1, 1, 1,
                2,
                3, 3,
                4,
                5, 5,
                6,
                7, 7, 7, 7,
                8, 8, 8, 8,
                9, 9,
                10, 10,
                11, 11, 11, 11
        };

        int[] merged = merge(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10);
        System.out.println(areArraysEqual(expected, merged));
        printArray(merged);

//        printArray(a1);
//        printArray(a2);
//        printArray(a3);
//        printArray(merge(a1, a2, a10));
    }

    private static int[] merge(int[]... arrays) {
        if (arrays.length == 0) {
            return new int[0];
        }
        if (arrays.length == 1) {
            return arrays[0];
        }
        if (arrays.length == 2) {
            return mergeTwo(arrays[0], arrays[1]);
        }

        int newArraysLength = arrays.length / 2;
        if (arrays.length % 2 != 0) {
            newArraysLength++;
        }
        int[][] newArrays = new int[newArraysLength][];

        if (arrays.length % 2 == 0) {
            for (int i = 0; i < arrays.length; i += 2) {
                newArrays[i / 2] = mergeTwo(arrays[i], arrays[i + 1]);
            }
        } else {
            for (int i = 0; i < arrays.length - 1; i += 2) {
                newArrays[i / 2] = mergeTwo(arrays[i], arrays[i + 1]);
            }
            newArrays[newArrays.length - 1] = arrays[arrays.length - 1];
        }

        return merge(newArrays);
    }

    private static int[] mergeTwo(int[] a, int[] b) {
        if (a.length == 0 && b.length == 0) {
            return a;
        }
        if (a.length == 0) {
            return b;
        }
        if (b.length == 0) {
            return a;
        }

        int[] result = new int[a.length + b.length];
        int i = 0;
        int j = 0;

        while (i < a.length && j < b.length) {
            if (a[i] == b[j]) {
                result[i + j] = a[i++];
                result[i + j] = b[j++];
                continue;
            }
            if (a[i] < b[j]) {
                result[i + j] = a[i++];
                continue;
            }
            if (a[i] > b[j]) {
                result[i + j] = b[j++];
                continue;
            }
        }
        for (; i < a.length; i++) {
            result[i + j] = a[i];
        }
        for (; j < b.length; j++) {
            result[i + j] = b[j];
        }
        return result;
    }

    private static void printArray(int[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
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