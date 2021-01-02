package org.wit.hillforts.room

import android.content.Context
import androidx.room.Room
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.models.UserModel
import org.wit.hillforts.models.UserStore

class UserStoreRoom(val context: Context) : UserStore {

    var dao: UserDao

    init {
        val database = Room.databaseBuilder(context, Database::class.java, "room_sample.db")
            .fallbackToDestructiveMigration()
            .build()
        dao = database.userDao()
    }

    override fun findAll(): List<UserModel> {
        return dao.findAll()
    }

    override fun findOne(email: String): UserModel? {
        return dao.findOne(email)
    }

  //  override fun findById(id: Long): UserModel? {
  //      return dao.findById(id)
  //  }

    override fun create(user: UserModel) {
        dao.create(user)
    }

    override fun update(user: UserModel) {
        dao.update(user)
    }

    override fun delete(user: UserModel) {
        dao.deleteUser(user)
    }


}