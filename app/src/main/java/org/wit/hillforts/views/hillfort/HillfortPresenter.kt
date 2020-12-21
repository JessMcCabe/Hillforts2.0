package org.wit.hillforts.views.hillfort


import android.content.Intent
import org.wit.hillforts.helpers.showImagePicker
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.models.Location
import org.wit.hillforts.views.BasePresenter
import org.wit.hillforts.views.BaseView
import org.wit.hillforts.views.VIEW


class HillfortPresenter (view: BaseView) : BasePresenter(view) {

    val IMAGE_REQUEST1 = 1
    val IMAGE_REQUEST2 = 2
    val IMAGE_REQUEST3 = 3
    val IMAGE_REQUEST4 = 4
    val LOCATION_REQUEST = 2

    var hillfort = HillfortModel()
    var defaultLocation = Location(52.245696, -7.139102, 15f)
    //var app: MainApp
    var edit = false;

    init {
       // app = view.application as MainApp
        if (view.intent.hasExtra("hillfort_edit")) {
            edit = true
            hillfort = view.intent.extras?.getParcelable<HillfortModel>("hillfort_edit")!!
            view.showHillfort(hillfort)
            //view.ratingBar2.rating = hillfort.rating
        }
    }

    fun doAddOrSave(user: Long,title: String, description: String, dateVisited: String, additionalNotes: String, rating: Float) {
        hillfort.userId = user
        hillfort.title = title
        hillfort.description = description
        hillfort.dateVisited = dateVisited
        hillfort.additionalNotes = additionalNotes
        hillfort.rating = rating
        if (edit) {
            app.hillforts.update(hillfort)
        } else {
            app.hillforts.create(hillfort)
        }
        view?.finish()
    }

    fun doCancel() {
        view?.finish()
    }

    fun doDelete() {
        app.hillforts.delete(hillfort)
        view?.finish()
    }

    fun doSelectImage1() {
        view?.let {
            showImagePicker(view!!, IMAGE_REQUEST1)
        }
    }
    fun doSelectImage2() {
        view?.let {
            showImagePicker(view!!, IMAGE_REQUEST2)
        }
    }
    fun doSelectImage3() {
        view?.let {
            showImagePicker(view!!, IMAGE_REQUEST3)
        }
    }
    fun doSelectImage4() {
        view?.let {
            showImagePicker(view!!, IMAGE_REQUEST4)
        }
    }

    fun doSetLocation() {
        if (edit == false) {
            view?.navigateTo(VIEW.LOCATION, LOCATION_REQUEST, "location", defaultLocation)
        } else {
            view?.navigateTo(
                VIEW.LOCATION,
                LOCATION_REQUEST,
                "location",
                Location(hillfort.lat, hillfort.lng, hillfort.zoom)
            )
        }
    }
    override   fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            IMAGE_REQUEST1 -> {
                hillfort.image1 = data.data.toString()
                view?.showHillfort(hillfort)
            }
            LOCATION_REQUEST -> {
                val location = data.extras?.getParcelable<Location>("location")!!
                hillfort.lat = location.lat
                hillfort.lng = location.lng
                hillfort.zoom = location.zoom
            }
        }
    }
}