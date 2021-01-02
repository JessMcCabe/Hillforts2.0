package org.wit.hillforts.views.HillfortFavourite

import org.wit.hillforts.views.hillfortlist.HillfortAdapter
import org.wit.hillforts.views.hillfortlist.HillfortListPresenter
import org.wit.hillforts.views.hillfortlist.HillfortListener



import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import org.wit.hillforts.R
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.views.BaseView


class HillfortFavouriteView :  BaseView(), HillfortListener {

    lateinit var presenter: HillfortFavouritePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort_list)
        setSupportActionBar(toolbar)
        super.init(toolbar, true)

        presenter = HillfortFavouritePresenter(this)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        presenter.loadHillforts()
    }

    override fun showHillforts(hillforts: List<HillfortModel>) {

        recyclerView.adapter = HillfortAdapter(hillforts, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.item_add -> presenter.doAddHillfort()
            R.id.item_map -> presenter.doShowHillfortsMap()
            R.id.btn_logout ->presenter.doLogout()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onHillfortClick(hillfort: HillfortModel) {
        presenter.doEditHillfort(hillfort)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenter.loadHillforts()
        super.onActivityResult(requestCode, resultCode, data)
    }
}