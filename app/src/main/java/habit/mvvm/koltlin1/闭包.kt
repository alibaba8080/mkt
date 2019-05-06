package habit.mvvm.koltlin1

/**
----------------------------------------------------------------------------------------------

 *项目: Koltlin1      habit.mvvm.koltlin1
 *创建: panshengtao  邮箱：1274218999@lecent.com
 *时间: 2019年05月05日 4:10 PM


 *描述:
--------------------------------------------------------------------------------------------*/


fun main(args: Array<String>) {
    var result = test()
    result()
    result()
    result()
}


fun test(): () -> Unit {
    var a = 10

    return {
        println(a)
        a++
    }

}