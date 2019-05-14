package habit.koltlin1

/**
----------------------------------------------------------------------------------------------

 *项目: Koltlin1      habit.koltlin1
 *创建: panshengtao  邮箱：1274218999@lecent.com
 *时间: 2019年04月29日 8:40 PM


 *描述:
----------------------------------------------------------------------------------------------
 */
//函数独立于对象 单独存在  函数内的函数——>嵌套函数

//main函数是你顶层函数
fun main(args: Array<String>) {
    show("张三")
    getLength("kkk")
    println(getString())




    fun show(){
        println("hello world")
    }

    show()
}


fun show(){
    println("hello world")
}

fun show(name:String){
    println(name)
}

fun getLength(naem:String):Int{

    return naem.length
}

fun getString():String{
    return "张三"
}