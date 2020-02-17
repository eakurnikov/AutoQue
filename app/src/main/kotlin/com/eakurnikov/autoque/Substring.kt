package com.eakurnikov.autoque

import kotlin.collections.HashSet

fun main() {
    val s1 = "aaaaaaabbssupcjsoidc" //8
    val s2 = "aaaa" //0
    val s3 = "" //0
    val s4 = "a" //1
    val s5 = "aaabbbccc" //2
    val s6 = "aaabcde" //5
    val s7 = "abcdeff" //6

    println(sub(s1))
}

fun sub(str: String): Pair<String, Int> {
    if (str.isEmpty()) {
        return Pair("", 0)
    }
    if (str.length == 1) {
        return Pair(str, 1)
    }

    var result: MutableSet<Char> = HashSet()
    var current: MutableSet<Char> = HashSet()

    for (i in 0 until str.length) {
        current.add(str[i])

        for (j in i+1 until str.length) {
            if (!current.contains(str[j])) {
                current.add(str[j])
            } else {
                if (current.size > result.size) {
                    result = current
                }
                current = HashSet()
                current.add(str[j])
                break
            }
        }

        if (current.size > result.size) {
            result = current
        }
        current = HashSet()
    }

    return result.toList().toString() to result.size
}