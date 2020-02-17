package com.eakurnikov.autoque

import java.lang.IllegalArgumentException

class MyList<T> {
    var size: Int = 0
    var head: Entry<T>? = null
    var tail: Entry<T>? = null

    init {
        head?.next = tail
    }

    fun getValue(index: Int): T? {
        if (head == null) {
            return null
        }

        var current = head
        var counter = 0

        while (counter <= index && current != null) {
            current = current.next
            counter++
        }

        return current?.value
    }

    fun getEntry(index: Int): Entry<T>? {
        if (head == null) {
            return null
        }

        var current = head
        var counter = 0

        while (counter <= index && current != null) {
            current = current.next
            counter++
        }

        return current
    }

    fun add(index: Int, value: T) {
        var newEntry = Entry(null, null, value)

        var left = getEntry(index) ?: throw IllegalArgumentException()
        var right = left.next

        left.next = newEntry
        newEntry.prev = left

        right?.prev = newEntry
        newEntry.next = right ?: tail
    }

    fun search(value: T): Int {
        if (head == null) {
            return -1
        }

        var current = head
        var counter = 0

        while (current != null) {
            if (current.value == value) {
                return counter
            }
            current = current.next
            counter++
        }

        return counter
    }

    fun contains(value: T): Boolean {
        if (head == null) {
            return false
        }

        var current = head
        var counter = 0

        while (current != null) {
            if (current.value == value) {
                return true
            }
            current = current.next
            counter++
        }

        return false
    }
}

data class Entry<T>(
    var next: Entry<T>?,
    var prev: Entry<T>?,
    var value: T?
)
//) {
//    class Head<T>(next: Entry<T>?, prev: Entry<T>?, value: T?) : Entry<T>(next, prev, value)
//    class Member<T>(next: Entry<T>?, prev: Entry<T>?, value: T?) : Entry<T>(next, prev, value)
//    class Tail<T>(next: Entry<T>?, prev: Entry<T>?, value: T?) : Entry<T>(next, prev, value)
//}