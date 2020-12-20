package org.wit.hillforts.room

import androidx.room.*
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.models.UserModel

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(user: UserModel)

    @Query("SELECT * FROM UserModel")
    fun findAll(): List<UserModel>


    @Query("select * from UserModel where id = :id")
    fun findById(id: Long): UserModel

    @Query("select * from UserModel where email = :email")
    fun findOne(email: String): UserModel

    @Update
    fun update(user: UserModel)

    @Delete
    fun deleteUser(user: UserModel)
}