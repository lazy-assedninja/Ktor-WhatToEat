package me.lazy_assedninja.db

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.datetime

object Favorites : Table() {
    val id = integer("id").autoIncrement().index()
    val userID = integer("user_id")
        .references(Users.id, onDelete = ReferenceOption.NO_ACTION)
    val storeID = integer("store_id")
        .references(Stores.id, onDelete = ReferenceOption.NO_ACTION)
    val createTime = datetime("create_time")
    val updateTime = datetime("update_time")
}