package com.pst.mkt.koltlin1

/**
----------------------------------------------------------------------------------------------

 *项目: Koltlin1      habit.koltlin1
 *创建: panshengtao  邮箱：1274218999@lecent.com
 *时间: 2019年04月30日 4:09 PM


 *描述:
--------------------------------------------------------------------------------------------*/


fun main(args: Array<String>) {
    var zhHuman1= ZhHuman()
    zhHuman1.color="红色"
    zhHuman1.language=""
}


abstract class Human {
    abstract var color: String
    abstract var language: String
    abstract fun eat()
}


open class ZhHuman : Human() {

    override var color: String = "yellow"
    override var language: String = "Chinese"

    override fun eat() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

abstract class Animal{
    abstract var name:String
}

abstract class Cat: Animal(){
}