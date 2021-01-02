package org.wit.hillforts.views.HillfortFavourite



import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.doAsync

import org.jetbrains.anko.uiThread

import org.wit.hillforts.models.HillfortModel

import org.wit.hillforts.views.BasePresenter
import org.wit.hillforts.views.BaseView
import org.wit.hillforts.views.VIEW



class HillfortFavouritePresenter(view: BaseView) : BasePresenter(view) {

    fun doAddHillfort() {
        view?.navigateTo(VIEW.HILLFORT)
    }

    fun doEditHillfort(hillfort: HillfortModel) {
        view?.navigateTo(VIEW.HILLFORT, 0, "hillfort_edit", hillfort)
    }

    fun doShowHillfortsMap() {
        view?.navigateTo(VIEW.MAPS)
    }

    fun doShowHillforts() {
        view?.navigateTo(VIEW.LIST)
    }

    fun loadHillforts() {
        doAsync {
            val fav : Boolean = true
            val hillfortsAll = app.hillforts.findAll()
            val hillforts = hillfortsAll.filter { hillfort -> hillfort.fav == fav }
            uiThread {
                view?.showHillforts(hillforts)
            }
        }
    }

    fun doLogout() {
        FirebaseAuth.getInstance().signOut()
        app.hillforts.clear()
        view?.navigateTo(VIEW.LOGIN)
    }
}