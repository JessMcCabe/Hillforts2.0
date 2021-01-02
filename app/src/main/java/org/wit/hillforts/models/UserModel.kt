package org.wit.hillforts.models



import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class UserModel(  @PrimaryKey(autoGenerate = true)  var id: Long = 0,
                         var email: String = "",
                         var password: String ="",
                         var firstName: String ="",
                         var lastName: String =""
                         ) : Parcelable


