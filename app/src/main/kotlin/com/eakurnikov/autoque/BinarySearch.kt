package com.eakurnikov.autoque

import java.lang.IllegalArgumentException

fun main() {
//    val n = readLine()!!.trim().toInt()

    val a1 = intArrayOf()
    val a2 = intArrayOf(1) // -1
    val a3 = intArrayOf(3, 3, 3) //1
    val a4 = intArrayOf(0, 1, 2, 3) //3
    val a5 = intArrayOf(0, 1, 2, 3, 4) //3
    val a6 = intArrayOf(0, 1, 2, 5, 6, 7, 9, 10, 11, 13, 14, 17, 20, 2002, 2005) //13

//    println(binSearch(a1, 4))
    println(recBinSearch(a2, 3, 0, a2.size))
    println(recBinSearch(a3, 3, 0, a3.size))
    println(recBinSearch(a4, 3, 0, a4.size))
    println(recBinSearch(a5, 3, 0, a5.size))
    println(recBinSearch(a6, 2002, 0, a6.size))
}

private fun binSearch(array: IntArray, value: Int): Int {
    if (array.isEmpty()) {
        throw IllegalArgumentException()
    }

    var first = 0
    var last = array.size

    while (first < last) {
        val mid = (first + last) / 2

        if (array[mid] == value) {
            return mid
        }

        if (array[mid] > value) {
            last = mid - 1
        } else {
            first = mid + 1
        }
    }

    return if (first == array.size || array[first] != value) -1 else first
}

private fun recBinSearch(array: IntArray, value: Int, start: Int, end: Int): Int {
    require(array.isNotEmpty())

    if (start < end) {
        val mid = (start + end) / 2

        if (array[mid] == value) {
            return mid
        }

        return if (array[mid] > value) {
            recBinSearch(array, value, start, mid - 1)
        } else {
            recBinSearch(array, value, mid + 1, end)
        }
    }

    return if (start == array.size || array[start] != value) -1 else start
}