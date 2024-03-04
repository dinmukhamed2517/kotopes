package kz.sd.kotopes.firebase

import androidx.annotation.DrawableRes

data class Ad(
    var title:String? =null,
    var location:String? = null,
    @DrawableRes var imageUrl:Int?= null,
)