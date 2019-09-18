package com.pst.mkt.koltlin1

/**
----------------------------------------------------------------------------------------------

 *项目: Koltlin1      habit.koltlin1
 *创建: panshengtao  邮箱：1274218999@lecent.com
 *时间: 2019年04月29日 9:37 PM


 *描述:
--------------------------------------------------------------------------------------------*/

fun main(args: Array<String>) {
    var str="abcdefgh"

    for (c in str) {
        if (c=='c')continue

        println(c)
    }
}