package me.lazy_assedninja.vo

data class Report(

    val id: Int,
    val content: String,
    val createTime: String,

    val storeID: Int,
    val storeName: String?,
    val userID: Int,
    val userName: String?,
    val userPicture: String?
)