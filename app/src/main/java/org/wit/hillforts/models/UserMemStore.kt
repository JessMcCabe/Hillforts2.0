package org.wit.hillforts.models



import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastUserId =0L

internal fun getUserId(): Long{
    return lastUserId++
}

 class UserMemStore : UserStore, AnkoLogger {

    val users = ArrayList<UserModel>()

    override fun findAll(): List<UserModel> {
        return users
    }

    override fun create(user: UserModel) {
        users.add(user)
        logAll();
    }


    override fun update(user: UserModel) {
        var foundUser: UserModel? = users.find { u -> u.id == user.id }
        if (foundUser != null) {
            foundUser.email = user.email
            foundUser.password = user.password

            logAll()
        }
    }

    override fun delete(user: UserModel) {
        var foundUser: UserModel? = users.find { u -> u.id == user.id }
        users.remove(foundUser)
        logAll()
    }

     override fun findOne(email: String): UserModel? {
        return users.find { u -> u.email == email }

    }

    fun logAll(){
        users.forEach{info("${it}")}
    }
}