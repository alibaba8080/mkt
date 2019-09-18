package com.pst.mkt.koltlin1

/**
----------------------------------------------------------------------------------------------

 *项目: Koltlin1      habit.koltlin1
 *创建: panshengtao  邮箱：1274218999@lecent.com
 *时间: 2019年04月29日 9:26 PM


 *描述:
----------------------------------------------------------------------------------------------
 */

fun main(args: Array<String>) {
    var str="adf"

    for (a in str){
        println(a)
    }
    for ((a,n) in str.withIndex()){
        println("inex=$a  char=$n")
    }


    str.forEach {
        println(it)
    }

    str.forEachIndexed { index, c ->
        println("$index  $c")
    }
}