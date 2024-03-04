package kz.sd.kotopes.firebase

data class User(
    var name: String? = null,
    var pictureUrl: String? = null,
    var favoriteList: Map<String, Animal> = emptyMap()
)