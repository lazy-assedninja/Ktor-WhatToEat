package me.lazy_assedninja.db

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.datetime

object Stores : Table("stores") {
    val id = integer("id").autoIncrement().index()
    val placeID = text("place_id")
    val name = text("name")
    val address = text("address")
    val phone = text("phone")
    val picture = text("picture").nullable()
    val latitude = text("latitude")
    val longitude = text("longitude")
    val website = text("website").nullable()
    val star = float("star").nullable()
    val createTime = datetime("create_time")
    val updateTime = datetime("update_time")

    val tagID = integer("tag_id")
        .references(Tags.id, onDelete = ReferenceOption.NO_ACTION).nullable()
}