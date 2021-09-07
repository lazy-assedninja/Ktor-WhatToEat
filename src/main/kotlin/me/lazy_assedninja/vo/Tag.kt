package me.lazy_assedninja.vo

import kotlinx.serialization.Serializable

@Serializable
data class Tag(

    val id: Int,
    val name: String
)