package habit.koltlin1.属性委托

import kotlin.reflect.KProperty

/**
----------------------------------------------------------------------------------------------

 *项目: Koltlin1      habit.koltlin1
 *创建: panshengtao  邮箱：1274218999@lecent.com
 *时间: 2019年05月05日 10:55 AM


 *描述:
--------------------------------------------------------------------------------------------*/


fun main(args: Array<String>) {
    var bigHeadSon=BigHeadSon()
    bigHeadSon.压岁钱=150
    println(bigHeadSon.压岁钱)
}

class BigHeadSon{
    var 压岁钱=10
}

class Mother{

    operator fun getValue(bigHeadSon: BigHeadSon,property: KProperty<*>):Int{
        return 儿子的压岁钱
    }

    operator fun setValue(bigHeadSon: BigHeadSon,property: KProperty<*>,i:Int){
        儿子的压岁钱+=50
        自己的压岁钱=i-50
    }
    var 儿子的压岁钱=0
    var 自己的压岁钱=0

    companion object {
        var name="lll"
    }
}

//单列
class Utils private constructor(){


    companion object {
        var name="张三"
        val instant by lazy { Utils() }
    }

}