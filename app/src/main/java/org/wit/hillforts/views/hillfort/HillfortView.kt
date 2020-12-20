package org.wit.hillforts.views.hillfort

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
import org.wit.hillforts.main.MainApp
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.helpers.readImageFromPath
import org.wit.hillforts.models.UserModel
import android.widget.RatingBar
import kotlinx.android.synthetic.main.activity_hillfort.description
import kotlinx.android.synthetic.main.activity_hillfort.hillfortTitle


class HillfortView : AppCompatActivity(), AnkoLogger {
  var user = UserModel()
  lateinit var presenter: HillfortPresenter
  var hillfort = HillfortModel()
  lateinit var app : MainApp


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContentView(R.layout.activity_hillfort)
    toolbarAdd.title = title
    val rBar = findViewById<RatingBar>(R.id.ratingBar2)
    setSupportActionBar(toolbarAdd)
    info("Hillfort Activity started..")


    presenter = HillfortPresenter(this)
    user = intent.extras?.getParcelable("user")!!


    info("the hillfort is ........${hillfort}")


    if (hillfort.visited) {
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
      rBar?.rating = hillfort.rating
      hillfortImage1.setImageBitmap(readImageFromPath(this, hillfort.image1))
      hillfortImage2.setImageBitmap(readImageFromPath(this, hillfort.image2))
      hillfortImage3.setImageBitmap(readImageFromPath(this, hillfort.image3))
      hillfortImage4.setImageBitmap(readImageFromPath(this, hillfort.image4))
      btnAdd.setText(R.string.save_hillfort)
    }

    btnAdd.setOnClickListener() {

      if (hillfortTitle.text.toString().isEmpty()) {
        toast(R.string.enter_hillfort_title)
      } else {

          presenter.doAddOrSave(user.id,hillfortTitle.text.toString(),
            description.text.toString(),
            dateVisited.text.toString(),
            additionalNotes.text.toString(),
            rBar
          )


       // info("add Button Pressed: ${hillfort}")
        setResult(RESULT_OK)
        finish()

      }
    }
    checkBox.setOnClickListener() {

      if (hillfort.visited) {
        checkBox.isChecked = false
        hillfort.visited = false

      } else
        if (!hillfort.visited) {
          hillfort.visited = true
          checkBox.isChecked = true

        }
      app.hillforts.update(hillfort.copy())

      setResult(RESULT_OK)

      //finish()

    }
    chooseImage1.setOnClickListener {
      presenter.doSelectImage1()
    }

    chooseImage2.setOnClickListener {
      presenter.doSelectImage2()
    }


    chooseImage3.setOnClickListener {
      presenter.doSelectImage3()
    }

    chooseImage4.setOnClickListener {
      presenter.doSelectImage4()
    }

    hillfortLocation.setOnClickListener {
      presenter.doSetLocation()
    }


  }

  fun showHillfort(hillfort: HillfortModel) {
   hillfortTitle.setText(hillfort.title)
    description.setText(hillfort.description)
    dateVisited.setText(hillfort.dateVisited)
    additionalNotes.setText(hillfort.additionalNotes)
    hillfort.rating = ratingBar2.rating
    hillfortImage1.setImageBitmap(readImageFromPath(this, hillfort.image1))
    if (hillfort.image1 != null) {
      chooseImage1.setText(R.string.change_hillfort_image1)
    }
    hillfortImage2.setImageBitmap(readImageFromPath(this, hillfort.image2))
    if (hillfort.image2 != null) {
      chooseImage2.setText(R.string.change_hillfort_image2)
    }
    hillfortImage3.setImageBitmap(readImageFromPath(this, hillfort.image3))
    if (hillfort.image3 != null) {
      chooseImage3.setText(R.string.change_hillfort_image3)
    }
    hillfortImage4.setImageBitmap(readImageFromPath(this, hillfort.image4))
    if (hillfort.image4 != null) {
      chooseImage4.setText(R.string.change_hillfort_image4)
    }
    btnAdd.setText(R.string.save_hillfort)
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
        presenter.doDelete()
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

}