package org.wit.hillforts.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hillforts.models.HillfortJSONStore
import org.wit.hillforts.models.HillfortStore


class MainApp : Application(), AnkoLogger {

    //val hillforts = ArrayList<HillfortModel>()
   lateinit var hillforts : HillfortStore

    override fun onCreate() {
        super.onCreate()
        hillforts = HillfortJSONStore(applicationContext)
        info("Hillfort started")

    }
}