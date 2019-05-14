package basebata.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

/**
----------------------------------------------------------------------------------------------

 *项目: Koltlin1      BaseData.dao
 *创建: panshengtao  邮箱：1274218999@lecent.com
 *时间: 2019年05月07日 9:37 AM


 *描述:
--------------------------------------------------------------------------------------------*/

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE uid in(:userIds)")
    fun loadAllByIds(userIds: Array<Int>): List<User>

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): User

    @Insert
    fun add(user:User)

    @Delete
    fun deleteUser(user: User)
}