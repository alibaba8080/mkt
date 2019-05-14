package habit.koltlin1

/**
----------------------------------------------------------------------------------------------

 *项目: Koltlin1      habit.koltlin1
 *创建: panshengtao  邮箱：1274218999@lecent.com
 *时间: 2019年05月05日 2:35 PM


 *描述:
--------------------------------------------------------------------------------------------*/

fun main(args: Array<String>) {
    println(WEEK.星期一)

    var result = WEEK.values()

    for (week in result) {
        todo(week)
    }


}


enum class WEEK {
    星期一, 星期二, 星期三, 星期四, 星期五, 星期六, 星期日,
}


fun todo(week: WEEK) {

    when (week) {
        in WEEK.星期一..WEEK.星期五 -> {
            println("上班")
        }
        in WEEK.星期六..WEEK.星期日 -> {
            println("休息")
        }
    }

}

//数据类型

data class News(var name:String){};