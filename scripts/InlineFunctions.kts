#!/usr/bin/env kotlin

import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

inline fun <T> lock(lock: Lock, body: ()->T) : T{
    lock.lock()
    val v = body()
    lock.unlock()
    return v
}


val l = ReentrantLock()

val s: String = lock(l) {
    "Hi"
}

println(s)