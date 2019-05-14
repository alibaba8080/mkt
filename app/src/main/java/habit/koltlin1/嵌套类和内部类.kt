package habit.koltlin1

/**
----------------------------------------------------------------------------------------------

 *项目: Koltlin1      habit.koltlin1
 *创建: panshengtao  邮箱：1274218999@lecent.com
 *时间: 2019年04月30日 5:10 PM


 *描述:
--------------------------------------------------------------------------------------------*/
fun main(args: Array<String>) {
    var innerClass=OutClass().InnerClass()
    var innerClass1=OutClass1.InnerClass()
    innerClass.sayHello()
    innerClass1.sayHello()
}

class OutClass{
    var name="张三"
   inner class InnerClass{
        var name="李四"
        fun sayHello(){
            println("你好${this@OutClass.name}")
        }
    }
}


class OutClass1{
    var name="张三"
     class InnerClass{
        var name="李四"
        fun sayHello(){
            println("你好")
        }
    }
}