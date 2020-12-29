package org.wit.hillforts.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId =0L

internal fun getId(): Long{
    return lastId++
}

class HillfortMemStore : HillfortStore, AnkoLogger {

    val hillforts = ArrayList<HillfortModel>()

    override fun findAll(): List<HillfortModel> {
        return hillforts
    }

    override fun create(hillfort: HillfortModel) {
        hillforts.add(hillfort)
        logAll();
    }


    override fun update(hillfort: HillfortModel) {
        var foundHillfort: HillfortModel? = hillforts.find { h -> h.id == hillfort.id }
        if (foundHillfort != null) {
            foundHillfort.title = hillfort.title
            foundHillfort.description = hillfort.description
            foundHillfort.additionalNotes = hillfort.additionalNotes
            foundHillfort.visited = hillfort.visited
            foundHillfort.dateVisited = hillfort.dateVisited
            foundHillfort.image1 = hillfort.image1
            foundHillfort.image2 = hillfort.image2
            foundHillfort.image3 = hillfort.image3
            foundHillfort.image4 = hillfort.image4
            foundHillfort.rating = hillfort.rating
           // foundHillfort.lat = hillfort.lat
           // foundHillfort.lng = hillfort.lng
           // foundHillfort.zoom = hillfort.zoom
            foundHillfort.location = hillfort.location
            logAll()
        }
    }

    override fun delete(hillfort: HillfortModel) {
        var foundHillfort: HillfortModel? = hillforts.find { h -> h.id == hillfort.id }
        hillforts.remove(foundHillfort)
        logAll()
    }

    fun logAll(){
        hillforts.forEach{info("${it}")}
    }

    override fun findById(id:Long) : HillfortModel? {
        return hillforts.find { it.id == id }
    }

    override fun clear() {
        hillforts.clear()
    }
}