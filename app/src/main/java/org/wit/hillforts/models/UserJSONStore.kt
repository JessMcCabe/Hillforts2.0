package org.wit.hillforts.models



import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.hillforts.helpers.*
import java.util.*

val JSON_FILE_USER = "users.json"
val gsonBuilderUser = GsonBuilder().setPrettyPrinting().create()
val listTypeUser = object : TypeToken<java.util.ArrayList<UserModel>>() {}.type

fun generateRandomIdUser(): Long {
    return Random().nextLong()
}

 class UserJSONStore : UserStore, AnkoLogger {

    val context: Context
    var users = mutableListOf<UserModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE_USER)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<UserModel> {
        return users
    }

    override fun create(user: UserModel) {
        user.id = generateRandomId()
        users.add(user)
        serialize()
    }


    override fun update(user: UserModel) {
        var foundUser: UserModel? = users.find { u -> u.id == user.id }
        if (foundUser != null) {
            foundUser.email = user.email
            foundUser.password = user.password

            serialize()
        }
    }

    override fun delete(user: UserModel) {
        var foundUser: UserModel? = users.find { u -> u.id == user.id }
        users.remove(foundUser)
        serialize()
    }

     override fun findOne(email: String): UserModel? {
        return users.find { u -> u.email == email }
    }

    private fun serialize() {
        val jsonString = gsonBuilderUser.toJson(users, listTypeUser)
        write(context, JSON_FILE_USER, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE_USER)
        users = Gson().fromJson(jsonString, listTypeUser)
    }
}