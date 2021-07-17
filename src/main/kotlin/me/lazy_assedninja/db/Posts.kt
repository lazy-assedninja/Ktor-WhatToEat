package me.lazy_assedninja.db

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.datetime

object Posts : Table() {
    val id = integer("id").autoIncrement().index()
    val storeID = integer("store_id")
        .references(Stores.id, onDelete = ReferenceOption.NO_ACTION)
    val title = text("title")
    val content = text("content")
    val createTime = datetime("create_time")
    val updateTime = datetime("update_time")
}