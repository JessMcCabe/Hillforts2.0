package org.wit.hillforts.activities

import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.wit.hillforts.main.MainApp
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.models.UserModel

class HillfortListPresenter (val view: HillfortListView) {

    var app: MainApp

    init {
        app = view.application as MainApp
    }

    fun getHillforts() = app.hillforts.findAll()

    fun doAddHillfort(user : UserModel, USER_REQUEST: Int) {
        view.startActivityForResult(view.intentFor<HillfortView>().putExtra("user", user),USER_REQUEST)
    }

    fun doEditHillfort(hillfort: HillfortModel, user : UserModel, USER_REQUEST: Int) {
        view.startActivityForResult(view.intentFor<HillfortView>().putExtra("hillfort_edit", hillfort)
            .putExtra("user", user)
            ,USER_REQUEST)
    }

    fun doShowHillfortsMap() {
        view.startActivity<HillfortMapsActivity>()
    }
}