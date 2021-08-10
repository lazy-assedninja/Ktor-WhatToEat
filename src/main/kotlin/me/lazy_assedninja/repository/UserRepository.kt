package me.lazy_assedninja.repository

import me.lazy_assedninja.db.GoogleAccounts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.lazy_assedninja.db.Users
import me.lazy_assedninja.dto.GoogleAccount
import me.lazy_assedninja.vo.Role
import me.lazy_assedninja.dto.User
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

class UserRepository {
    fun insert(data: User) {
        val roleValue = when (data.role) {
            Role.Manager.type -> Role.Manager
            Role.Business.type -> Role.Business
            else -> Role.User
        }
        transaction {
            Users.insert {
                it[email] = data.email
                it[password] = data.password
                it[name] = data.name
                it[headPortrait] = data.headPortrait
                it[role] = roleValue
                it[createTime] = DateTime.now()
                it[updateTime] = DateTime.now()
                it[googleAccountID] = null
            }
        }
    }

    suspend fun getUser(id: Int): User? {
        return withContext(Dispatchers.IO) {
            transaction {
                Users.leftJoin(GoogleAccounts)
                    .select { Users.id eq id }
                    .map { user ->
                        val googleAccount = user[Users.googleAccountID]?.let { googleAccountID ->
                            toGoogleAccount(user, googleAccountID)
                        }
                        toUser(user, googleAccount)
                    }.firstOrNull()
            }
        }
    }

    suspend fun getUser(email: String): User? {
        return withContext(Dispatchers.IO) {
            transaction {
                Users.leftJoin(GoogleAccounts)
                    .select { Users.email eq email }
                    .map { user ->
                        val googleAccount = user[Users.googleAccountID]?.let { googleAccountID ->
                            toGoogleAccount(user, googleAccountID)
                        }
                        toUser(user, googleAccount)
                    }.firstOrNull()
            }
        }
    }

    suspend fun getUserByGoogleAccountID(googleID: String): User? {
        return withContext(Dispatchers.IO) {
            transaction {
                Users.leftJoin(GoogleAccounts)
                    .select { GoogleAccounts.googleID eq googleID }
                    .map { user ->
                        val googleAccount = user[Users.googleAccountID]?.let { googleAccountID ->
                            toGoogleAccount(user, googleAccountID)
                        }
                        toUser(user, googleAccount)
                    }.firstOrNull()
            }
        }
    }

    suspend fun update(data: User) {
        return withContext(Dispatchers.IO) {
            transaction {
                Users.update({ Users.id eq data.id }) {
                    it[email] = data.email
                    it[password] = data.password
                    it[name] = data.name
                    it[headPortrait] = data.headPortrait
                    it[updateTime] = DateTime.now()
                }
            }
        }
    }

    private fun toUser(row: ResultRow, googleAccount: GoogleAccount?): User = User(
        id = row[Users.id].value,
        email = row[Users.email],
        password = row[Users.password],
        name = row[Users.name],
        headPortrait = row[Users.headPortrait],
        role = row[Users.role].name,
        verificationCode = row[Users.verificationCode],
        createTime = row[Users.createTime].toString("yyyy-MM-dd"),
        updateTime = row[Users.updateTime].toString("yyyy-MM-dd"),

        isBounded = googleAccount == null,
        googleAccount = googleAccount
    )


    private fun toGoogleAccount(row: ResultRow, googleAccountID: Int): GoogleAccount = GoogleAccount(
        id = googleAccountID,
        googleID = row[GoogleAccounts.googleID],
        email = row[GoogleAccounts.email],
        name = row[GoogleAccounts.name],
        pictureURL = row[GoogleAccounts.pictureURL],
        createTime = row[GoogleAccounts.createTime].toString("yyyy-MM-dd"),
        updateTime = row[GoogleAccounts.updateTime].toString("yyyy-MM-dd")
    )
}