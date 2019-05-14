package habit.koltlin1

/**
----------------------------------------------------------------------------------------------

 *项目: Koltlin1      habit.koltlin1
 *创建: panshengtao  邮箱：1274218999@lecent.com
 *时间: 2019年05月05日 10:38 AM


 *描述:
--------------------------------------------------------------------------------------------*/


fun main(args: Array<String>) {
//    var smallHeadFeather=SmallHeadFeather()
//    smallHeadFeather.wash()
//
//    var bigHeadSon = BigHeadSon()
//    var smallHeadFeather = SmallHeadFeather(bigHeadSon)
//    smallHeadFeather.wash()

    var mother = Mother()
    mother.set(object : WashPower {
        override fun wash() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    })

    var ff = FF()

    ff.ff {
        println(it)
    }

}


interface WashPower {
    fun wash()
}


class BigHeadSon : WashPower {
    override fun wash() {
        println("大头儿子洗碗")
    }
}

//class SmallHeadFeather:WashPower by BigHeadSon(){
//
//}

class SmallHeadFeather(var washPower: WashPower) : WashPower by washPower {

    override fun wash() {
        println("小头儿子开始洗碗")
        washPower.wash()
        println("小头儿子洗碗好了")
    }

}


class Mother {
    var washPower: WashPower? = null
    fun set(washPower: WashPower) {
        this.washPower = washPower
        this.washPower!!.wash()
    }
}

//函数委托（接口回调）


class FF {
    fun ff(e: (String) -> Unit) {
        e.invoke("sdfsadf")
    }
}