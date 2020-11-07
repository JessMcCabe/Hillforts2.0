package org.wit.hillforts.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hillforts.models.HillfortJSONStore
import org.wit.hillforts.models.HillfortStore
import org.wit.hillforts.models.UserJSONStore
import org.wit.hillforts.models.UserStore


class MainApp : Application(), AnkoLogger {

    //val hillforts = ArrayList<HillfortModel>()
    lateinit var hillforts : HillfortStore
    lateinit var users : UserStore
    override fun onCreate() {
        super.onCreate()
        hillforts = HillfortJSONStore(applicationContext)
        users = UserJSONStore(applicationContext)
        info("Hillfort started")

    }
}