package basebata.dao

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

/**
----------------------------------------------------------------------------------------------

 *项目: Koltlin1      BaseData.dao
 *创建: panshengtao  邮箱：1274218999@lecent.com
 *时间: 2019年05月07日 9:53 AM


 *描述:
--------------------------------------------------------------------------------------------*/


@Database(entities = arrayOf(User::class), version = 1,exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun UserDao(): UserDao

    companion object {
        private val DB_NAME = "user.db"
        @Volatile
        var userDatabase:UserDatabase?=null
        fun getInstance(context: Context): UserDatabase? {
            if (userDatabase==null){
                synchronized(UserDatabase::class.java){
                    userDatabase= create(context)
                }
            }
            return userDatabase!!
        }
        fun create(context: Context): UserDatabase {
            return Room.databaseBuilder(
                context,
                UserDatabase::class.java,
                DB_NAME
            ).build()
        }
    }


}
