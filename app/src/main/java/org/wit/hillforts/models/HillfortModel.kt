package org.wit.hillforts.models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class HillfortModel(@PrimaryKey(autoGenerate = true)var id: Long = 0,
                         var userId: Long = 0,
                         var visited: Boolean = false,
                         var dateVisited: String = "",
                         var title: String = "",
                         var description: String ="",
                         var additionalNotes: String ="",
                         var image1: String = "",
                         var image2: String = "",
                         var image3: String = "",
                         var image4: String = "",
                         var rating: Float = 0f,
                         var lat : Double = 0.0,
                         var lng: Double = 0.0,
                         var zoom: Float = 0f) : Parcelable


@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable