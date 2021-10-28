package me.lazy_assedninja.po

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.jodatime.datetime

object GoogleAccounts : IntIdTable(name = "google_account") {

    val googleID = varchar("google_id", 25).index()
    val email = text("email")
    val name = text("name")
    val pictureURL = text("picture_url").nullable()
    val createTime = datetime("create_time")
    val updateTime = datetime("update_time")
}