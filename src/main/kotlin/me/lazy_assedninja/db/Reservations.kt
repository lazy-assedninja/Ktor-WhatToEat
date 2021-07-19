package me.lazy_assedninja.db

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.jodatime.datetime

object Reservations : IntIdTable() {
    val name = text("name")
    val phone = text("phone")
    val amount = text("amount")
    val time = text("time")
    val createTime = datetime("create_time")
    val updateTime = datetime("update_time")

    val storeID = integer("store_id")
        .references(Stores.id, onDelete = ReferenceOption.NO_ACTION)
    val userID = integer("user_id")
        .references(Users.id, onDelete = ReferenceOption.NO_ACTION)
}