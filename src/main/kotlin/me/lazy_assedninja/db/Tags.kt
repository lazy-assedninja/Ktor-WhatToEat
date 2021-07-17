package me.lazy_assedninja.db

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.datetime

object Tags : Table("tags") {
    val id = integer("id").autoIncrement().index()
    val name = text("name")
    val createTime = datetime("create_time")
    val updateTime = datetime("update_time")
}