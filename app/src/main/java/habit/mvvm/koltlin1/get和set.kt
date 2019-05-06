package habit.mvvm.koltlin1

/**
----------------------------------------------------------------------------------------------

 *项目: Koltlin1      habit.mvvm.koltlin1
 *创建: panshengtao  邮箱：1274218999@lecent.com
 *时间: 2019年04月30日 12:33 PM


 *描述:
--------------------------------------------------------------------------------------------*/


fun main(args: Array<String>) {

}

class Person{
    var name="张三"
    private set
    var age=20
    set(value) {
        if(value<150)
            field=value
    }

}