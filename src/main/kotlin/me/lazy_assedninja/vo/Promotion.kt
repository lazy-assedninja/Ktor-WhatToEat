package me.lazy_assedninja.vo

data class Promotion(

    val id: Int,
    val title: String,
    val picture: String,
    val discount: String,
    val notice: String,
    val deadline: String,

    val storeID: Int,
    val storeName: String? = null,
)