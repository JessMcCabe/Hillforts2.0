package org.wit.hillforts.views.hillfort


import android.annotation.SuppressLint
import android.content.Intent
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.wit.hillforts.R
import org.wit.hillforts.helpers.checkLocationPermissions
import org.wit.hillforts.helpers.createDefaultLocationRequest
import org.wit.hillforts.helpers.isPermissionGranted
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
    val LOCATION_REQUEST = 6

    var map: GoogleMap? = null
    var hillfort = HillfortModel()
    var defaultLocation = Location(52.245696, -7.139102, 15f)
    var edit = false;
    var locationManualyChanged = false;
    var locationService: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(view)
    val locationRequest = createDefaultLocationRequest()
    val rBar = hillfort.rating
    init {
        if (view.intent.hasExtra("hillfort_edit")) {
            edit = true
            hillfort = view.intent.extras?.getParcelable<HillfortModel>("hillfort_edit")!!

            view.showHillfort(hillfort)
        } else {
            if (checkLocationPermissions(view)) {
                doSetCurrentLocation()
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun doSetCurrentLocation() {
        locationService.lastLocation.addOnSuccessListener {
            locationUpdate(Location(it.latitude, it.longitude))
        }
    }

    @SuppressLint("MissingPermission")
    fun doResartLocationUpdates() {
        var locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult != null && locationResult.locations != null) {
                    val l = locationResult.locations.last()
                    if (!locationManualyChanged) {
                        locationUpdate(Location(l.latitude, l.longitude))
                    }
                }
            }
        }
        if (!edit) {
            locationService.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }

    override fun doRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (isPermissionGranted(requestCode, grantResults)) {
            doSetCurrentLocation()
        } else {
            locationUpdate(defaultLocation)
        }
    }

    fun cacheHillfort (title: String, description: String, visited: Boolean ) {
        hillfort.title = title;
        hillfort.description = description
        hillfort.visited = visited

    }

    fun doConfigureMap(m: GoogleMap) {
        map = m
        locationUpdate(hillfort.location)
    }

    fun locationUpdate(location: Location) {
        hillfort.location = location
        hillfort.location.zoom = 15f
        map?.clear()
        val options = MarkerOptions().title(hillfort.title).position(LatLng(hillfort.location.lat, hillfort.location.lng))
        map?.addMarker(options)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(hillfort.location.lat, hillfort.location.lng), hillfort.location.zoom))
        view?.showLocation(hillfort.location)
    }

    fun doAddOrSave(
        title: String,
        description: String,
        rating: Float,
        dateVisited: String,
        additionalNotes: String,
        visited: Boolean,
        fav: Boolean
         ) {
        hillfort.title = title
        hillfort.description = description
        hillfort.rating = rating
        hillfort.dateVisited = dateVisited
        hillfort.additionalNotes = additionalNotes
        hillfort.visited = visited
        hillfort.fav = fav
       // hillfort.location = location

        doAsync {
            if (edit) {
                app.hillforts.update(hillfort)
            } else {
                app.hillforts.create(hillfort)
            }
            uiThread {
                view?.finish()
            }
        }
    }

    fun doCancel() {
        view?.finish()
    }

    fun doDelete() {
        doAsync {
            app.hillforts.delete(hillfort)
            uiThread {
                view?.finish()
            }
        }
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
        locationManualyChanged = true;
        view?.navigateTo(VIEW.LOCATION, LOCATION_REQUEST, "location", Location(hillfort.location.lat, hillfort.location.lng, hillfort.location.zoom))
    }

    override fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            IMAGE_REQUEST1 -> {
                hillfort.image1 = data.data.toString()
                view?.showHillfort(hillfort)
            }
            IMAGE_REQUEST2 -> {
                hillfort.image2 = data.data.toString()
                view?.showHillfort(hillfort)
            }
            IMAGE_REQUEST3 -> {
                hillfort.image3 = data.data.toString()
                view?.showHillfort(hillfort)
            }
            IMAGE_REQUEST4 -> {
                hillfort.image4 = data.data.toString()
                view?.showHillfort(hillfort)
            }
            LOCATION_REQUEST -> {
                val location = data.extras?.getParcelable<Location>("location")!!
                hillfort.location = location
                locationUpdate(location)
            }
        }
    }

    fun doAddToFav() {
        hillfort.fav = !hillfort.fav
        if(!hillfort.fav){
            view?.add_rem_fav?.setText(R.string.add_to_Favourite)
        }
        if(hillfort.fav){
            view?.add_rem_fav?.setText(R.string.remove_from_Favourite)
        }
    }

}