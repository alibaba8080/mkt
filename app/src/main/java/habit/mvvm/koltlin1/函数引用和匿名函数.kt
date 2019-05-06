package habit.mvvm.koltlin1

/**
----------------------------------------------------------------------------------------------

 *项目: Koltlin1      habit.mvvm.koltlin1
 *创建: panshengtao  邮箱：1274218999@lecent.com
 *时间: 2019年04月29日 10:48 PM


 *描述:
--------------------------------------------------------------------------------------------*/


fun main(args: Array<String>) {

    var padd=::add

    println(padd(12, 12))
    println(padd.invoke(12, 12))

    var kadd:(Int,Int)->Int={a,b ->a+b}

    println(kadd.invoke(12, 12))
}
fun add(a:Int,b:Int) = a+b
