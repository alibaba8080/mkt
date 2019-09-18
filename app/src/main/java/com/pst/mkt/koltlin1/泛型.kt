package com.pst.mkt.koltlin1

/**
----------------------------------------------------------------------------------------------

 *项目: Koltlin1      habit.koltlin1
 *创建: panshengtao  邮箱：1274218999@lecent.com
 *时间: 2019年04月30日 5:24 PM


 *描述:
--------------------------------------------------------------------------------------------*/

fun main(args: Array<String>) {
    parseType(1)

    var list = ArrayList<Apple>()
    setFruit(list)

    var listFruit=ArrayList<Fruit>()
    setThing(listFruit)

    setAnyThing(list)

    setAnyThing1(listFruit)

}

open class Box<T>(var thing: T) {
}

class FruitBox<T>(thing: T) : Box<T>(thing)

class FruitBox1<Fruit>(thing: Fruit) : Box<Fruit>(thing)

class FruitBox2(thing: Apple) : Box<Fruit>(thing)

abstract class Thing
abstract class Fruit : Thing()

class Apple : Fruit()


fun <T> parseType(thing: T) {
    when (thing) {
        is Int -> println("是Int类型")
        else -> println("位置类型")
    }
}


fun setFruit(arr: ArrayList<out Fruit>) {
    println(arr)
}


fun setThing(arr: ArrayList<in Fruit>) {
    println(arr)
}

fun setAnyThing(arr:ArrayList<*>){
    println(arr)
}

fun setAnyThing1(arr:ArrayList<*>){
    println(arr)
}
