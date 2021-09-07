package me.lazy_assedninja.po

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.jodatime.datetime

object Stores : IntIdTable() {

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