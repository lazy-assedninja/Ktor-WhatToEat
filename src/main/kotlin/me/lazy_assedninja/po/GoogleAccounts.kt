package me.lazy_assedninja.po

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.jodatime.datetime

object GoogleAccounts : IntIdTable(name = "google_account") {

    val googleID = text("google_id").index()
    val email = text("email")
    val name = text("name")
    val pictureURL = varchar("picture_url", 100).nullable()
    val createTime = datetime("create_time")
    val updateTime = datetime("update_time")
}