package org.wit.hillforts.views.hillfort

import android.content.Intent


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import org.wit.hillforts.R
import org.wit.hillforts.models.HillfortModel
import com.bumptech.glide.Glide
import com.google.android.gms.maps.GoogleMap
import kotlinx.android.synthetic.main.activity_hillfort.description
import kotlinx.android.synthetic.main.activity_hillfort.hillfortTitle
import org.wit.hillforts.models.Location
import org.wit.hillforts.views.BaseView


class HillfortView : BaseView(), AnkoLogger {
  lateinit var presenter: HillfortPresenter
  var hillfort = HillfortModel()
  lateinit var map: GoogleMap

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_hillfort)

    super.init(toolbarAdd, true);
    mapView.onCreate(savedInstanceState);
    mapView.getMapAsync {
      map = it
      presenter.doConfigureMap(map)
      it.setOnMapClickListener { presenter.doSetLocation() }
    }

    presenter = initPresenter(HillfortPresenter(this)) as HillfortPresenter

    chooseImage1.setOnClickListener {
      presenter.cacheHillfort(hillfort.title, description.text.toString(), hillfort.visited )
      presenter.doSelectImage1()
    }
    chooseImage2.setOnClickListener {
      presenter.cacheHillfort(hillfort.title, description.text.toString(),hillfort.visited)
      presenter.doSelectImage2()
    }
    chooseImage3.setOnClickListener {
      presenter.cacheHillfort(hillfort.title, description.text.toString(),hillfort.visited)
      presenter.doSelectImage3()
    }
    chooseImage4.setOnClickListener {
      presenter.cacheHillfort(hillfort.title, description.text.toString(),hillfort.visited)
      presenter.doSelectImage4()
    }

    checkBox.setOnClickListener() {

      if (checkBox.isChecked) {
        //checkBox.isChecked = true
        hillfort.visited = true


      } else
        if (!checkBox.isChecked) {
          hillfort.visited = false
         // checkBox.isChecked = false


        }
    }
  }

  override fun showHillfort(hillfort: HillfortModel) {
    if (hillfortTitle.text.isEmpty()) hillfortTitle.setText(hillfort.title)
    if (description.text.isEmpty()) description.setText(hillfort.description)
    ratingBar2.rating = hillfort.rating
    dateVisited.setText(hillfort.dateVisited)
    additionalNotes.setText(hillfort.additionalNotes)
    Glide.with(this).load(hillfort.image1).into(hillfortImage1);
    Glide.with(this).load(hillfort.image2).into(hillfortImage2);
    Glide.with(this).load(hillfort.image3).into(hillfortImage3);
    Glide.with(this).load(hillfort.image4).into(hillfortImage4);
    if (checkBox.isChecked) {
      //checkBox.isChecked = true
      checkBox.toggle()
    }

    if (hillfort.image1 != "") {
      chooseImage1.setText(R.string.change_hillfort_image1)
    }
    if (hillfort.image2 != "") {
      chooseImage2.setText(R.string.change_hillfort_image2)
    }
    if (hillfort.image3 != "") {
      chooseImage3.setText(R.string.change_hillfort_image3)
    }
    if (hillfort.image4 != "") {
      chooseImage4.setText(R.string.change_hillfort_image4)
    }
    this.showLocation(hillfort.location)


    //if (hillfort.visited) {
     // checkBox.toggle()
   // }


  }

  override fun showLocation (loc : Location) {
    lat.setText("%.6f".format(loc.lat))
    lng.setText("%.6f".format(loc.lng))
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_hillfort, menu)
    if (presenter.edit) menu.getItem(0).setVisible(true)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item?.itemId) {
      R.id.item_delete -> {
        presenter.doDelete()
      }
      R.id.item_save -> {
        if (hillfortTitle.text.toString().isEmpty()) {
          toast(R.string.enter_hillfort_title)
        } else {

          presenter.doAddOrSave(hillfortTitle.text.toString(), description.text.toString(),ratingBar2.rating, dateVisited.text.toString(), additionalNotes.text.toString()
          , hillfort.visited )

        }
      }
      R.id.item_cancel -> {
        finish()
      }
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (data != null) {
      presenter.doActivityResult(requestCode, resultCode, data)
    }
  }

  override fun onBackPressed() {
    presenter.doCancel()
  }

  override fun onDestroy() {
    super.onDestroy()
    mapView.onDestroy()
  }

  override fun onLowMemory() {
    super.onLowMemory()
    mapView.onLowMemory()
  }

  override fun onPause() {
    super.onPause()
    mapView.onPause()
  }

  override fun onResume() {
    super.onResume()
    mapView.onResume()
    presenter.doResartLocationUpdates()
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    mapView.onSaveInstanceState(outState)
  }


}