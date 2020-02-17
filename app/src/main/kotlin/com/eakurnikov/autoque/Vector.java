package com.eakurnikov.autoque;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Vector {

    public static void main(String... args) {
        Vector vector = new Vector();

        int[] vec1 = {0}; //0
        int[] vec2 = {1}; // 1
        int[] vec3 = {}; //0
        int[] vec4 = {0, 1, 1, 0}; //2
        int[] vec5 = {0, 1, 0, 1, 0}; //1
        int[] vec6 = {0, 1, 1, 1, 0}; //3
        int[] vec7 = {1, 1, 1, 1, 1}; // 5
        int[] vec8 = {0, 0, 0, 0, 0}; //0

        System.out.println(vector.run(vec1));
        System.out.println(vector.run(vec2));
        System.out.println(vector.run(vec3));
        System.out.println(vector.run(vec4));
        System.out.println(vector.run(vec5));
        System.out.println(vector.run(vec6));
        System.out.println(vector.run(vec7));
        System.out.println(vector.run(vec8));

        Map<Integer, int[]> map = new HashMap<>();
        for (Map.Entry<Integer, int[]> entry: map.entrySet()) {
            int[] second = map.get(10 - entry.getKey());
        }

    }

    private int run(int[] vec) {
        if (vec.length == 0) {
            return 0;
        }
        if (vec.length == 1) {
            return vec[0] == 1 ? 1 : 0;
        }

        int counter = 0;
        int max = 0;

        for (int i = 0;  i < vec.length; i ++) {
            if (vec[i] == 0) {
                counter = 0;
                continue;
            }

            counter++;
            if (counter > max) {
                max = counter;
            }
        }

        return max;
    }
}