package basebata.dao

import android.arch.persistence.room.*

/**
----------------------------------------------------------------------------------------------

 *项目: Koltlin1      BaseData.dao
 *创建: panshengtao  邮箱：1274218999@lecent.com
 *时间: 2019年05月06日 5:28 PM


 *描述:
--------------------------------------------------------------------------------------------*/


@Entity(tableName = "user")
data class User @Ignore constructor(

    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0,

    @ColumnInfo(name = "first_name")
    var firstName: String? = null,

    @ColumnInfo(name = "last_name")
    var lastName: String? = null
)







