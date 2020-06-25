#!/usr/bin/env kotlin

val numbers = mutableListOf<Int>(3,2,1)
numbers.also {println("Populating the list with additional values")}
    .apply {
        add(5)
        add(4)
    }
    .also {println("Sorting the list")}
    .sort()
println(numbers)

println(mutableListOf<Int>(3,2,1)
    .apply{
        add(5)
        add(4)
    }
    .sorted()
    .toList()
)

