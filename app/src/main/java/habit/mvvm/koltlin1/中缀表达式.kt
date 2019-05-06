package habit.mvvm.koltlin1

/**
----------------------------------------------------------------------------------------------

 *项目: Koltlin1      habit.mvvm.koltlin1
 *创建: panshengtao  邮箱：1274218999@lecent.com
 *时间: 2019年05月05日 10:26 AM


 *描述:
--------------------------------------------------------------------------------------------*/


fun main(args: Array<String>) {

    var 李四=Person1()
    李四.sayHelloTo("张三")

    李四 sayHelloTo2 "张三"

}


class Person1(){
    fun sayHelloTo(name:String){
        println("你好$name")
    }

    infix  fun sayHelloTo2(name:String){
        println("你好$name")
    }
}