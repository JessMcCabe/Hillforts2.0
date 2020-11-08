package org.wit.hillforts.activities

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.wit.hillforts.R
import org.wit.hillforts.main.MainApp
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.models.UserModel


class HillfortListActivity : AppCompatActivity(), HillfortListener, AnkoLogger {
    var user = UserModel()

    lateinit var app: MainApp
    val USER_REQUEST = 8


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_hillfort_list)
        app = application as MainApp
        user = intent.extras?.getParcelable("user")!!

        toolbar.title = title
        setSupportActionBar(toolbar)
        info("In Hillfort List Activity, user is..${user}")
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        //recyclerView.adapter = HillfortAdapter(app.hillforts)
        loadHillforts()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        info("In Hillfort List Activity, user is..${item}")
        when (item?.itemId) {

            R.id.item_add -> startActivityForResult(intentFor<HillfortActivity>().putExtra("user", user),USER_REQUEST)

            R.id.btn_logout -> startActivityForResult<LoginActivity>(0)
            R.id.btn_settings -> startActivityForResult(intentFor<SettingsActivity>().putExtra("user", user)
                .putExtra("hillforts_number",numOfHillforts())
                .putExtra("hillforts_visited",numOfHillfortsVisited()),USER_REQUEST)
        }
        return super.onOptionsItemSelected(item)
    }



    override fun onHillfortClick(hillfort: HillfortModel) {

        startActivityForResult(intentFor<HillfortActivity>().putExtra("hillfort_edit", hillfort)
            .putExtra("user", user)
            ,USER_REQUEST

        )

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadHillforts()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun loadHillforts() {
        val allHillforts = app.hillforts.findAll()
        val userHillforts = allHillforts.filter { hillfort -> hillfort.userId == user.id  }

        showHillforts(userHillforts)
    }

    fun showHillforts (hillforts: List<HillfortModel>) {
        recyclerView.adapter = HillfortAdapter(hillforts, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun numOfHillforts(): Int {
        val allHillforts = app.hillforts.findAll()
        val userHillforts = allHillforts.filter { hillfort -> hillfort.userId == user.id  }

        return userHillforts.size
    }


    private fun numOfHillfortsVisited(): Int {
        val allHillforts = app.hillforts.findAll()
        val userHillfortVisited = allHillforts.filter { hillfort -> hillfort.visited }

        return userHillfortVisited.size
    }
}


