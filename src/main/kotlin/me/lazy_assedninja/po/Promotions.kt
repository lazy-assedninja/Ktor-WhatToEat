package me.lazy_assedninja.po

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.jodatime.datetime

object Promotions : IntIdTable() {

    val title = text("title")
    val picture = text("picture")
    val discount = text("discount")
    val notice = text("notice")
    val deadline = datetime("deadline")
    val createTime = datetime("create_time")

    val storeID = integer("store_id")
        .references(Stores.id, onDelete = ReferenceOption.NO_ACTION)
}