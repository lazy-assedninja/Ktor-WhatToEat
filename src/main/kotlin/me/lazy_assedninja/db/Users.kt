package me.lazy_assedninja.db

import me.lazy_assedninja.vo.Role
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.datetime

object Users : Table("users") {
    val id = integer("id").autoIncrement().index()
    val email = text("email").index()
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