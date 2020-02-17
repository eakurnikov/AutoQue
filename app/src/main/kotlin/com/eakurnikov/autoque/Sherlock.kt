package com.eakurnikov.autoque

import kotlin.math.abs

fun main() {
    println(isValid("aabbcd"))
}

fun isValid(s: String): String {
    if (s.length == 1) return "YES"

    val map1 = HashMap<Char, Int>()
    for (c in s) {
        map1[c] = (map1[c] ?: 0) + 1
    }
    val map2 = HashMap<Int, Int>()
    for (e in map1) {
        map2[e.value] = (map2[e.value] ?: 0) + 1
    }
    return when(map2.size) {
        1 -> "YES"
        2 -> {
            val keys = map2.keys.toList()
            val values = map2.values.toList()

            for (i in 0..1) {
                val otherIndex = if (i == 0) 1 else 0
                if (values[i] == 1 &&
                    (keys[i] - 1 == keys[otherIndex] || keys[i] - 1 == 0)
                ) {
                    return "YES"
                }
            }
            return "NO"
        }
        else -> "NO"
    }
}