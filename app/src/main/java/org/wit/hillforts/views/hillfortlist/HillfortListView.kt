package org.wit.hillforts.views.hillfortlist

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import org.jetbrains.anko.*
import org.wit.hillforts.R
import org.wit.hillforts.activities.LoginActivity
import org.wit.hillforts.activities.SettingsActivity
import org.wit.hillforts.main.MainApp
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.models.UserModel


class HillfortListView : AppCompatActivity(), HillfortListener, AnkoLogger {
    var user = UserModel()

    lateinit var presenter: HillfortListPresenter
    val USER_REQUEST = 8


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_hillfort_list)
        presenter = HillfortListPresenter(this)
        user = intent.extras?.getParcelable("user")!!

        toolbar.title = title
        setSupportActionBar(toolbar)
        info("In Hillfort List Activity, user is..${user}")
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = HillfortAdapter(presenter.getHillforts(), this)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        info("In Hillfort List Activity, user is..${item}")
        when (item?.itemId) {

            R.id.item_add -> presenter.doAddHillfort(user, USER_REQUEST)
            R.id.item_map -> presenter.doShowHillfortsMap()
            R.id.btn_logout -> startActivityForResult<LoginActivity>(0)
            R.id.btn_settings -> startActivityForResult(intentFor<SettingsActivity>().putExtra("user", user)
                .putExtra("hillforts_number",numOfHillforts())
                .putExtra("hillforts_visited",numOfHillfortsVisited()),USER_REQUEST)
        }
        return super.onOptionsItemSelected(item)
    }



    override fun onHillfortClick(hillfort: HillfortModel) {

        presenter.doEditHillfort(hillfort, user, USER_REQUEST)



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadHillforts()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun loadHillforts() {
        val allHillforts = presenter.getHillforts()
        val userHillforts = allHillforts.filter { hillfort -> hillfort.userId == user.id  }

        showHillforts(userHillforts)
    }

    fun showHillforts (hillforts: List<HillfortModel>) {
        recyclerView.adapter = HillfortAdapter(hillforts, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun numOfHillforts(): Int {
        val allHillforts = presenter.getHillforts()
        val userHillforts = allHillforts.filter { hillfort -> hillfort.userId == user.id  }

        return userHillforts.size
    }


    private fun numOfHillfortsVisited(): Int {
        val allHillforts = presenter.getHillforts()
        val userHillfortVisited = allHillforts.filter { hillfort -> hillfort.visited }

        return userHillfortVisited.size
    }
}


