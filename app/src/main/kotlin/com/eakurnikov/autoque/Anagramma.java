package com.eakurnikov.autoque;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Anagramma {

    public static void main(String... args) {
        System.out.println(areAnagrammas("abcdef", "fabdec")); //true
        System.out.println(areAnagrammas("abcdef", "xyz")); //false
        System.out.println(areAnagrammas("abcdef", "")); // false
        System.out.println(areAnagrammas("abcdef", "a")); //false
        System.out.println(areAnagrammas("abcdef", "xfabdecz")); //false
        System.out.println(areAnagrammas("abcdef", "abde")); //false
        System.out.println(areAnagrammas("aabcd", "abbcd")); //false
        System.out.println(areAnagrammas("", "")); //true
    }

    private static boolean areAnagrammas(String a, String b) {
        if (a.length() == 0 && b.length() == 0) {
            return true;
        }
        if (a.length() == 0 || b.length() == 0) {
            return false;
        }

        Map<Character, Integer> mapA = new HashMap<>();
        for (int i = 0; i < a.length(); i++) {
            char c = a.charAt(i);
            final Integer cInMap = mapA.get(c);
            mapA.put(c, cInMap == null ? 1 : cInMap + 1);
        }

        Map<Character, Integer> mapB = new HashMap<>();
        for (int i = 0; i < b.length(); i++) {
            char c = b.charAt(i);
            final Integer cInMap = mapB.get(c);
            mapB.put(c, cInMap == null ? 1 : cInMap + 1);
        }

        return mapA.equals(mapB);
    }
}