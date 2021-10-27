package me.lazy_assedninja.po

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.jodatime.datetime

object Tags : IntIdTable(name = "tag") {

    val name = text("name")
    val createTime = datetime("create_time")
    val updateTime = datetime("update_time")
}