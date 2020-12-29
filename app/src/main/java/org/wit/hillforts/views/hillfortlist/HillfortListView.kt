package org.wit.hillforts.views.hillfortlist

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import org.jetbrains.anko.*
import org.wit.hillforts.R
import org.wit.hillforts.activities.SettingsActivity
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.models.UserModel
import org.wit.hillforts.views.BaseView


class HillfortListView : BaseView(), HillfortListener, AnkoLogger {
    var user = UserModel()
    var upEnabled = true
    lateinit var presenter: HillfortListPresenter
    val USER_REQUEST = 8


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_hillfort_list)
        presenter = HillfortListPresenter(this)
        //user = intent.extras?.getParcelable("user")!!
        val allHillforts = presenter.getHillforts()
        val userHillforts = allHillforts.filter { hillfort -> hillfort.userId == -2544457592191868715 }//user.id  }
        toolbar.title = title
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(upEnabled)
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            toolbar.title = "${title}: ${user.email}"
        }
        info("In Hillfort List Activity, user is..${user}")
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = HillfortAdapter(userHillforts, this)
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
            R.id.btn_logout ->presenter.doLogout()
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

    override fun showHillforts (hillforts: List<HillfortModel>) {
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


