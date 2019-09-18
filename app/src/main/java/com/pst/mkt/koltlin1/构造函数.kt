package com.pst.mkt.koltlin1

/**
----------------------------------------------------------------------------------------------

 *项目: Koltlin1      habit.koltlin1
 *创建: panshengtao  邮箱：1274218999@lecent.com
 *时间: 2019年04月30日 12:47 PM


 *描述:
--------------------------------------------------------------------------------------------*/


fun main(args: Array<String>) {
    var personer = Personer("张三", 21, "")
    println(personer.name + personer.age)
}

//class  Personer(name:String,age:Int){
//    var name="张三"
//    var age=20
//    init {
//        this.name=name
//        this.age=age
//    }
//}


class Personer(var name: String, var age: Int) {

    var phone = ""

    constructor(name: String, age: Int, phone: String) : this(name, age) {
        this.phone = phone
    }
}


open class Father {
    open fun habit() {

    }
}


class Son : Father() {
    override fun habit() {

    }
}