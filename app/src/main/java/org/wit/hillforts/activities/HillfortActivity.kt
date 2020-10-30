package org.wit.hillforts.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.hillforts.R
import org.wit.hillforts.helpers.showImagePicker
import org.wit.hillforts.main.MainApp
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.helpers.readImage
import org.wit.hillforts.helpers.readImageFromPath

class HillfortActivity : AppCompatActivity(), AnkoLogger {

  var hillfort = HillfortModel()
  var edit = false
  val IMAGE_REQUEST = 1
  lateinit var app : MainApp


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_hillfort)
    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)
    info("Hillfort Activity started..")

    app = application as MainApp

    if (intent.hasExtra("hillfort_edit")) {
      edit = true
      hillfort = intent.extras?.getParcelable("hillfort_edit")!!
      hillfortTitle.setText(hillfort.title)
      description.setText(hillfort.description)
      hillfortImage.setImageBitmap(readImageFromPath(this, hillfort.image))
      if (hillfort.image != null) {
        chooseImage.setText(R.string.change_hillfort_image)
      }
      btnAdd.setText(R.string.save_hillfort)
    }

    btnAdd.setOnClickListener() {
       hillfort.title = hillfortTitle.text.toString()
      hillfort.description = description.text.toString()
      if (hillfort.title.isEmpty()) {
        toast(R.string.enter_hillfort_title)
      }else{
        if(edit) {
          app.hillforts.update(hillfort.copy())
        } else {
          app.hillforts.create(hillfort.copy())
        }
        info("add Button Pressed: ${hillfort}")
        setResult(RESULT_OK)
        finish()
      }
    }

    chooseImage.setOnClickListener {
      showImagePicker(this, IMAGE_REQUEST)
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_hillfort, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item?.itemId) {
      R.id.item_cancel -> {
        finish()
      }
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when (requestCode) {
      IMAGE_REQUEST -> {
        if (data != null) {
          hillfort.image = data.getData().toString()
          hillfortImage.setImageBitmap(readImage(this, resultCode, data))
          chooseImage.setText(R.string.change_hillfort_image)
        }
      }
    }
  }
}