package kz.sd.kotopes.firebase

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.google.android.gms.maps.model.LatLng
import kotlinx.parcelize.Parcelize

@Parcelize

data class Location(
    val id: String,
    val name: String,
    val description: String,
    val latLng: LatLng,
    @DrawableRes val img:Int,
    val phoneNumber:String,
):Parcelable