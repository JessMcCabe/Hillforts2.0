package org.wit.hillforts.room

import androidx.room.Database
import androidx.room.RoomDatabase
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.models.UserModel
import org.wit.hillforts.room.HillfortDao

@Database(entities = arrayOf(HillfortModel::class, UserModel :: class), version = 3,  exportSchema = false)
abstract class Database : RoomDatabase() {

    abstract fun hillfortDao(): HillfortDao
    abstract fun userDao(): UserDao
}