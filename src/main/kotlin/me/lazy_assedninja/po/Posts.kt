package me.lazy_assedninja.po

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.jodatime.datetime

object Posts : IntIdTable() {

    val title = text("title")
    val content = text("content")
    val createTime = datetime("create_time")
    val updateTime = datetime("update_time")

    val storeID = integer("store_id")
        .references(Stores.id, onDelete = ReferenceOption.NO_ACTION)
}