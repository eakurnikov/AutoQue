package com.eakurnikov.autoque;

public class Brackets {

    private int callCounter = 0;

    public static void main(String[] args) {
        Brackets brackets = new Brackets();
        brackets.step("(", 1, 0, 5);
        System.out.println(brackets.callCounter);

        brackets = new Brackets();
        brackets.betterStep("(", 1, 0, 5);
        System.out.println(brackets.callCounter);
    }

    private void step(String input, int left, int right, int n) {
        callCounter++;
        if (left == right && left == n) {
            System.out.println(input);
            return;
        }
        if (left > n || right > left) {
            return;
        }
        step(input + "(", left + 1, right, n);
        step(input + ")", left, right + 1, n);
    }

    private void betterStep(String input, int left, int right, int n) {
        callCounter++;
        if (left == right && left == n) {
            System.out.println(input);
            return;
        }
        if (left + 1 <= n) {
            betterStep(input + "(", left + 1, right, n);
        }
        if (right + 1 <= left) {
            betterStep(input + ")", left, right + 1, n);
        }
    }
}
