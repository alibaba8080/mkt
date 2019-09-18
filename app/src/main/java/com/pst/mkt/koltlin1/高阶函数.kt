package com.pst.mkt.koltlin1

/**
----------------------------------------------------------------------------------------------

 *项目: Koltlin1      habit.koltlin1
 *创建: panshengtao  邮箱：1274218999@lecent.com
 *时间: 2019年05月05日 4:25 PM


 *描述:
--------------------------------------------------------------------------------------------*/


fun main(args: Array<String>) {
    var sum:Int={n:Int,m:Int-> m+n}.invoke(1,2);

    println(sum);

    { a: String ->
        println(a)
    }.invoke("aasdfsadfsadf");
}


