package org.wit.hillforts.activities

import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.hillforts.R
import org.wit.hillforts.helpers.showImagePicker
import org.wit.hillforts.main.MainApp
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.helpers.readImage
import org.wit.hillforts.helpers.readImageFromPath
import org.wit.hillforts.models.Location
import org.wit.hillforts.models.UserModel


class HillfortActivity : AppCompatActivity(), AnkoLogger {
  var user = UserModel()
  var hillfort = HillfortModel()
  lateinit var app : MainApp
  val IMAGE_REQUEST1 = 1
  val IMAGE_REQUEST2 = 3
  val IMAGE_REQUEST3 = 5
  val IMAGE_REQUEST4 = 6
  val LOCATION_REQUEST = 2
  //var location = Location(52.245696, -7.139102, 15f)





  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContentView(R.layout.activity_hillfort)
    toolbarAdd.title = title

    setSupportActionBar(toolbarAdd)
    info("Hillfort Activity started..")


    app = application as MainApp
    user = intent.extras?.getParcelable("user")!!


    info("the hillfort is ........${hillfort}")


  if(hillfort.visited){
    checkBox.toggle()
  }

    var edit = false

    if (intent.hasExtra("hillfort_edit")) {
      hillfort = intent.extras?.getParcelable("hillfort_edit")!!
      edit = true
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
      hillfort = intent.extras?.getParcelable("hillfort_edit")!!
      hillfortTitle.setText(hillfort.title)
      description.setText(hillfort.description)
      dateVisited.setText(hillfort.dateVisited)
      additionalNotes.setText(hillfort.additionalNotes)
      hillfortImage1.setImageBitmap(readImageFromPath(this, hillfort.image1))
      hillfortImage2.setImageBitmap(readImageFromPath(this, hillfort.image2))
      hillfortImage3.setImageBitmap(readImageFromPath(this, hillfort.image3))
      hillfortImage4.setImageBitmap(readImageFromPath(this, hillfort.image4))
      btnAdd.setText(R.string.save_hillfort)
    }

    btnAdd.setOnClickListener() {
      hillfort.userId = user.id
      hillfort.title = hillfortTitle.text.toString()
      hillfort.description = description.text.toString()
      hillfort.dateVisited = dateVisited.text.toString()
      hillfort.additionalNotes = additionalNotes.text.toString()
      if (hillfort.title.isEmpty()) {
        toast(R.string.enter_hillfort_title)
      } else {
        if (edit) {
          app.hillforts.update(hillfort.copy())
        } else {
          app.hillforts.create(hillfort.copy())
        }

        info("add Button Pressed: ${hillfort}")
        setResult(RESULT_OK)
        finish()

      }
    }
    checkBox.setOnClickListener() {

      if(hillfort.visited){
        checkBox.isChecked = false
        hillfort.visited = false

      }else
      if(!hillfort.visited){
        hillfort.visited = true
        checkBox.isChecked = true

      }
      app.hillforts.update(hillfort.copy())

        setResult(RESULT_OK)

        //finish()

    }
    chooseImage1.setOnClickListener {
      showImagePicker(this, IMAGE_REQUEST1)
    }

    chooseImage2.setOnClickListener {
      showImagePicker(this, IMAGE_REQUEST2)
    }


    chooseImage3.setOnClickListener {
      showImagePicker(this, IMAGE_REQUEST3)
    }

    chooseImage4.setOnClickListener {
      showImagePicker(this, IMAGE_REQUEST4)
    }

    hillfortLocation.setOnClickListener {
      val location = Location(52.245696, -7.139102, 15f)
      if (hillfort.zoom != 0f) {
        location.lat =  hillfort.lat
        location.lng = hillfort.lng
        location.zoom = hillfort.zoom
      }
      startActivityForResult (intentFor<MapActivity>().putExtra("location", location), LOCATION_REQUEST)
    }


  }


  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when (requestCode) {
      IMAGE_REQUEST1 -> {
        if (data != null) {
          hillfort.image1 = data.getData().toString()
          hillfortImage1.setImageBitmap(readImage(this, resultCode, data))
          chooseImage1.setText(R.string.change_hillfort_image1)
        }
      }
      IMAGE_REQUEST2 -> {
        if (data != null) {
          hillfort.image2 = data.getData().toString()
          hillfortImage2.setImageBitmap(readImage(this, resultCode, data))
          chooseImage2.setText(R.string.change_hillfort_image2)
        }
      }
      IMAGE_REQUEST3 -> {
        if (data != null) {
          hillfort.image3 = data.getData().toString()
          hillfortImage3.setImageBitmap(readImage(this, resultCode, data))
          chooseImage3.setText(R.string.change_hillfort_image3)
        }
      }
      IMAGE_REQUEST4 -> {
        if (data != null) {
          hillfort.image4 = data.getData().toString()
          hillfortImage4.setImageBitmap(readImage(this, resultCode, data))
          chooseImage4.setText(R.string.change_hillfort_image4)
        }
      }
      LOCATION_REQUEST -> {
        if (data != null) {
          val location = data.extras?.getParcelable<Location>("location")!!
          hillfort.lat = location.lat
          hillfort.lng = location.lng
          hillfort.zoom = location.zoom
          info("location is: $location")
        }
      }
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_hillfort, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.item_cancel -> {
        finish()
      }
      R.id.item_delete -> {
        app.hillforts.delete(hillfort)
        finish()
      }
    }
    return super.onOptionsItemSelected(item)
  }


}