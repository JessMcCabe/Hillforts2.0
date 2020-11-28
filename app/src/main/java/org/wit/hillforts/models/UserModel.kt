package org.wit.hillforts.models



import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(    var id: Long = 0,
                         var email: String = "",
                         var password: String ="",
                         var firstName: String ="",
                         var lastName: String =""
                         ) : Parcelable


