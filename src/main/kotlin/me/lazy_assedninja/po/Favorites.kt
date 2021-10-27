package me.lazy_assedninja.po

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.jodatime.datetime

object Favorites : IntIdTable(name = "favorite") {

    val createTime = datetime("create_time")
    val updateTime = datetime("update_time")

    val userID = integer("user_id")
        .references(Users.id, onDelete = ReferenceOption.NO_ACTION)
    val storeID = integer("store_id")
        .references(Stores.id, onDelete = ReferenceOption.NO_ACTION)
}