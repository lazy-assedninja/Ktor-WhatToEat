package me.lazy_assedninja.dto

import kotlinx.serialization.Serializable

@Serializable
data class Response<T>(
    val result: Int,
    val body: T
)