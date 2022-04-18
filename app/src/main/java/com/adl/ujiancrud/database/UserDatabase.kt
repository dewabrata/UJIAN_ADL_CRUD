package com.adl.ujiancrud.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.adl.ujiancrud.database.dao.UserDao
import com.adl.ujiancrud.database.model.UserModel

@Database(version = 1, entities = [UserModel::class])
abstract class UserDatabase :RoomDatabase() {

    abstract  fun userDao():UserDao


    companion object{

        var instance:UserDatabase?=null


        fun getInstance(ctx:Context):UserDatabase{

            if(instance == null){
                instance = Room.databaseBuilder(
                    ctx.applicationContext,
                    UserDatabase::class.java,
                    "user.db"
                ).fallbackToDestructiveMigration().build()
            }

            return instance!!

        }



    }

}