package com.eakurnikov.autoque

import java.lang.IllegalArgumentException

fun main() {
//    val n = readLine()!!.trim().toInt()
    val result = upgrFebRec(4, IntArray(4))
    println(result)
}

private fun fibRec(n: Int): Int {
    if (n == 0 || n == 1) {
        return 1
    }
    return fibRec(n - 1) + fibRec(n - 2)
}

private fun fibCycle(n: Int): Int {
    if (n == 0 || n == 1) {
        return 1
    }

    var prevprev = 1
    var prev = 1
    var current = 2

    for (i in 2..n) {
        current = prev + prevprev
        prevprev = prev
        prev = current
    }

    return current
}

private fun upgrFebRec(n: Int, buffer: IntArray): Int {
    if (n == 0 || n == 1) {
        return 1
    }
    if (buffer.size < n) {
        throw IllegalArgumentException()
    }

    var prevprev = buffer[n - 2]
    if (prevprev == 0) {
        prevprev = upgrFebRec(n - 2, buffer)
        buffer[n - 2] = prevprev
    }

    var prev = buffer[n - 1]
    if (prev == 0) {
        prev = upgrFebRec(n - 1, buffer)
        buffer[n - 1] = prev
    }

    return prev + prevprev
}