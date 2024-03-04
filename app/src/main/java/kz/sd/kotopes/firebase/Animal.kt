package kz.sd.kotopes.firebase

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class Animal(
    var id:String? = null,
    var name:String? = null,
    var description:String? = null,
    @DrawableRes var image:Int? = null,
    var type:String? = null,
    var favorite:Boolean? = false,
):Parcelable