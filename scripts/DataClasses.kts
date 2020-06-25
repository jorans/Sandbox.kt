#!/usr/bin/env kotlin

import java.util.function.BinaryOperator
import java.util.function.IntBinaryOperator

data class Person(val name: String, val age: Int)

val p1 = Person("Adam", 10)

println(p1)
println(p1.copy(age=12))

val (name, age) = p1
println("Name: $name, age:$age")

