package com.pst.mkt.koltlin1

/**



 *@创建人  pst

 *@创建时间  $date$

 *@描述:

 *@修改:

 */
    var s:String="Hello World"
    var score:Int=1
    fun main(args : Array<String>){
        val address="贵州省贵阳市观山湖区"
        val address2="""
            贵州省
            贵阳市
            观山湖区
        """.trimIndent()
        println("hello world")
        println("$score")
        println(address)
        println(address2)

//        var pair=Pair<String,Int>("张三",20)
        var pair="张三" to 20
        println(pair.first)
        println(pair.second)


        var triple=Triple<String,String,Int>("","",1)


        var str:String?=null

       var b:Int= str?.toInt()?:-1
        println(b)
    }
