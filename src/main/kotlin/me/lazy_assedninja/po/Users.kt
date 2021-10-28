package me.lazy_assedninja.po

import me.lazy_assedninja.vo.Role
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.jodatime.datetime

object Users : IntIdTable(name = "user") {

    val email = varchar("email", 100).index()
    val password = text("password")
    val name = text("name")
    val headPortrait = text("head_portrait")
    val role = enumerationByName("role", 20, Role::class)
    val verificationCode = varchar("verification_code", 6).nullable()
    val createTime = datetime("create_time")
    val updateTime = datetime("update_time")

    val googleAccountID = integer("google_account_id")
        .references(GoogleAccounts.id, onDelete = ReferenceOption.CASCADE).nullable()
}