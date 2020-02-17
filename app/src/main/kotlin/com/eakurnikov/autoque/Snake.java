package com.eakurnikov.autoque;

public class Snake {

    public static void main(String... args) {
        printMatrix(run(7));
    }

    private static int[][] run(int n) {
        if (n <= 0 || n % 2 == 0) {
            throw new IllegalArgumentException();
        }
        if (n == 1) {
            return new int[1][1];
        }

        int[][] result = new int[n][n];
        int centerIndex = n / 2;
        int i = centerIndex;
        int j = centerIndex;
        int value = 0;
        int cycleN = 1;


        while (i != n && j != 0) {
            result[i][j] = value++;
            j--;
            cycleN += 2;

            while (i > (n - cycleN) / 2) {
                result[i][j] = value++;
                i--;
            }
            while (j < (n + cycleN) / 2 - 1) {
                result[i][j] = value++;
                j++;
            }
            while (i < (n + cycleN) / 2 - 1) {
                result[i][j] = value++;
                i++;
            }
            while (j > (n - cycleN) / 2) {
                result[i][j] = value++;
                j--;
            }
        }
        result[i][j] = value;

        return result;
    }

    private static void printMatrix(int[][] matrix) {
        for (int j = 0; j < matrix[0].length; j++) {
            for (int i = 0; i < matrix.length; i++) {
                System.out.print((matrix[i][j] < 10 ? " " : "") + matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}

// 2 1 8
// 3 0 7
// 4 5 6

// 12 11 10  9 24
// 13  2  1  8 23
// 14  3  0  7 22
// 15  4  5  6 21
// 16 17 18 19 20

// 30 29 28 27 26 25 48
// 31 12 11 10  9 24 47
// 32 13  2  1  8 23 46
// 33 14  3  0  7 22 45
// 34 15  4  5  6 21 44
// 35 16 17 18 19 20 43
// 36 37 38 39 40 41 42