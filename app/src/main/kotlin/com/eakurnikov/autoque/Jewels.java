package com.eakurnikov.autoque;

import java.util.HashSet;
import java.util.Set;

public class Jewels {

    public static void main(String... args) {
        System.out.println(new Jewels().run("abcde", "axyzab"));
    }

    private int run(String J, String S) {
        Set<Character> setJ = new HashSet<>();

        for (int i = 0; i < J.length(); i++) {
            setJ.add(J.charAt(i));
        }

        int counter = 0;

        Set<Character> setS = new HashSet<>();

        for (int i = 0; i < S.length(); i++) {
            Character khar = S.charAt(i);

            boolean shouldCheck = setS.add(khar);

            if (shouldCheck && setJ.contains(khar)) {
                counter++;
            }
        }

        return counter;
    }
}