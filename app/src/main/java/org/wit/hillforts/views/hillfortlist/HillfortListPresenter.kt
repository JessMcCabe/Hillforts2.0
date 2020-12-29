package org.wit.hillforts.views.hillfortlist

import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.intentFor
import org.wit.hillforts.main.MainApp
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.models.UserModel
import org.wit.hillforts.views.VIEW
import org.wit.hillforts.views.hillfort.HillfortView


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

    fun doLogout() {
        FirebaseAuth.getInstance().signOut()
        view?.navigateTo(VIEW.LOGIN)
    }

    fun doShowHillfortsMap() {
        view?.navigateTo(VIEW.MAPS)
    }
}