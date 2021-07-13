package me.lazy_assedninja.db

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.datetime

object GoogleAccounts : Table("google_accounts") {
    val id = integer("id").autoIncrement().index()
    val googleID = text("google_id").index()
    val email = text("email")
    val name = text("name")
    val pictureURL = varchar("picture_url", 100).nullable()
    val createTime = datetime("create_time")
    val updateTime = datetime("update_time")
}