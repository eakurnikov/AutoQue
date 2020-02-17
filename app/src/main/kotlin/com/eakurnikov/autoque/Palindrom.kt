package com.eakurnikov.autoque

fun main() {
    val s0 = "abcdcba"
    val s1 = "abcdefg"
    val s2 = "abcdcda"
    val s3 = "abcddcba"
    val s4 = "abcdbcd"
    val s5 = ""
    val s6 = " "
    val s7 = "AbCdBa"
    val s8 = "a bcd e fg fed cba"
    val s9 = "А роза упала на лапу Азора"
    val s10 = "AbCdCBa"

//        s0.printIsPalindrom()
//        s1.printIsPalindrom()
//        s2.printIsPalindrom()
//        s3.printIsPalindrom()
//        s4.printIsPalindrom()
//        s5.printIsPalindrom()
//        s6.printIsPalindrom()
//        s7.printIsPalindrom()
//        s8.printIsPalindrom()
//        s9.printIsPalindrom()
//        s10.printIsPalindrom()

    "A man, a plan, a canal. Panama".printIsPalindrom()
}

fun String.printIsPalindrom() {
    System.out.println("\"$this\" is ${if (!isPalindrom()) "NOT" else ""} a palindrom}")
}

fun String.isPalindrom(): Boolean {
    val text: String = toLowerCase()
    var i = 0
    var j = length - 1

    while (i < length / 2) {
        while (!text[i].isLetterOrDigit() && i < length / 2) {
            i++
        }
        while (!text[j].isLetterOrDigit() && j > 0) {
            j--
        }
        if (text[i] != text[j]) {
            return false
        }
        i++
        j--
    }
    return true
}