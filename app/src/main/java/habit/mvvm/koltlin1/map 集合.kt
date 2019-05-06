package habit.mvvm.koltlin1

/**
----------------------------------------------------------------------------------------------

 *项目: Koltlin1      habit.mvvm.koltlin1
 *创建: panshengtao  邮箱：1274218999@lecent.com
 *时间: 2019年05月05日 3:55 PM


 *描述:
--------------------------------------------------------------------------------------------*/


fun main(args: Array<String>) {
        var l=mapOf("a" to "b","c" to "d")

        var k= mutableMapOf<String,String>()
        k.put("c","d")
        k.forEach { t,u ->
                println("$t    $u")
        }
}

