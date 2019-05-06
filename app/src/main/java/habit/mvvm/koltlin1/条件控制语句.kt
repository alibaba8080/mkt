package habit.mvvm.koltlin1

/**
----------------------------------------------------------------------------------------------

 *项目: Koltlin1      habit.mvvm.koltlin1
 *创建: panshengtao  邮箱：1274218999@lecent.com
 *时间: 2019年04月29日 9:15 PM


 *描述:
----------------------------------------------------------------------------------------------
 */

fun main(args: Array<String>) {

    println(max(3, 2))
}


fun max(a:Int,b:Int):Int{
//    if (a>b){
//        return a
//    }else{
//        return b
//    }

    return if (a>b)a else b
}