package habit.koltlin1

/**
----------------------------------------------------------------------------------------------

 *项目: Koltlin1      habit.koltlin1
 *创建: panshengtao  邮箱：1274218999@lecent.com
 *时间: 2019年04月30日 4:21 PM


 *描述:
--------------------------------------------------------------------------------------------*/


fun main(args: Array<String>) {
//    var xiaoMing=XiaoMing()
//    xiaoMing.ride()
//    xiaoMing.drive()

//    var my=My()
//    my.willDo()

    var you=You() as My
    you.willDo()
}

class XiaoMing:ZhHuman() ,RideBike,DriveCar{
    override var license: String="123145667"

    override fun drive() {

    }

    override fun ride() {
        println("小明q车")
    }

}

interface RideBike{
    fun ride()
}

interface DriveCar{
    var license:String
    fun drive(){
        println("小明开车")
    }
}

open class My(){

   open fun willDo(){
        println("I will go a way")
    }
}

class You:My(){
    override fun willDo() {
        println("You will go a way")
        super.willDo()
    }
}