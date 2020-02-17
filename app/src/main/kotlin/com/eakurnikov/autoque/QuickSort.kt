package com.eakurnikov.autoque

fun main() {
//    val n  = readLine()?.trim()?.toInt()
    split(intArrayOf(23, 1, 5, 106, 38, 52, 2, 3, 100), 9).print()

    val quickSorted = intArrayOf(23, 1, 5, 106, 38, 52, 2, 3, 100)
    quickSort(quickSorted, 0, 8)
    quickSorted.print()

//    split(intArrayOf(23, 1)).print()
}

fun split(a: IntArray, size: Int): IntArray {
    if (size < 2) {
        return a
    }

    var i = 0
    var j = size - 1

    var isPivotAtLeft = true

    while (i <= j) {
        if (a[i] >= a[j]) {
            val temp = a[j]
            a[j] = a[i]
            a[i] = temp
            isPivotAtLeft = !isPivotAtLeft
        }
        if (isPivotAtLeft) {
            j--
        } else {
            i++
        }
    }

    return a
}

fun quickSort(a: IntArray, start: Int, end: Int) {
    var i = start
    var j = end

    var isPivotAtLeft = true

    while (i != j) {
        if (a[i] > a[j]) {
            val temp = a[j]
            a[j] = a[i]
            a[i] = temp
            isPivotAtLeft = !isPivotAtLeft
        }
        if (isPivotAtLeft) {
            j--
        } else {
            i++
        }
    }

    if (i > start + 1) {
        quickSort(a, start, i - 1)
    }
    if (i < end - 1) {
        quickSort(a, i + 1, end)
    }
}

fun IntArray.print() {
    for (i in this) {
        print("$i ")
    }
    println()
}