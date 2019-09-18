package com.pst.mkt.koltlin1

/**
----------------------------------------------------------------------------------------------

 *项目: Koltlin1      habit.koltlin1
 *创建: panshengtao  邮箱：1274218999@lecent.com
 *时间: 2019年04月29日 9:45 PM


 *描述:
--------------------------------------------------------------------------------------------*/


fun main(args: Array<String>) {
    var str1="abc"
    var str2="123"

    tag@for (c1 in str1) {
        tag1@for (c2 in str2) {


            if(c1=='b'&&c2=='2'){
                break@tag1
            }
            println("$c1   $c2")
        }
    }
}