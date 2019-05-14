package habit.koltlin1

/**
----------------------------------------------------------------------------------------------

 *项目: Koltlin1      habit.koltlin1
 *创建: panshengtao  邮箱：1274218999@lecent.com
 *时间: 2019年04月30日 11:56 AM


 *描述:
--------------------------------------------------------------------------------------------*/


fun main(args: Array<String>) {

    var 助教=Teacher()
    println(助教.salary)
    var 讲师=助教++
    print(讲师.salary)

}

class Teacher{
    var level =1
    var salary=6000
    operator fun inc(): Teacher {
        this.level=level++
        this.salary=salary+500
        return this
    }



}