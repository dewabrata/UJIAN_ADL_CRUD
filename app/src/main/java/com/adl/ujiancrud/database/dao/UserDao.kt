package com.adl.ujiancrud.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.adl.ujiancrud.database.model.UserModel

@Dao
interface UserDao {

    @Insert
    fun insertUser(user:UserModel)

    @Update
    fun updateUser(user: UserModel)

    @Query("Select * from UserModel")
    fun getAll():List<UserModel>
}