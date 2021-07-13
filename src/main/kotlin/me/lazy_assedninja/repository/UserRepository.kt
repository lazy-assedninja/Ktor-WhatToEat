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

@Suppress("unused")
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
                Users.select { Users.id eq id }.firstOrNull()?.let { user ->
                    var googleAccount: GoogleAccount? = null
                    user[Users.googleAccountID]?.let {
                        googleAccount =
                            GoogleAccounts.select { GoogleAccounts.id eq it }.map { toGoogleAccount(it) }.firstOrNull()
                    }
                    toUser(user, googleAccount)
                }
            }
        }
    }

    suspend fun getUser(email: String): User? {
        return withContext(Dispatchers.IO) {
            transaction {
                Users.select { Users.email eq email }.firstOrNull()?.let { user ->
                    var googleAccount: GoogleAccount? = null
                    user[Users.googleAccountID]?.let {
                        googleAccount =
                            GoogleAccounts.select { GoogleAccounts.id eq it }.map { toGoogleAccount(it) }.firstOrNull()
                    }
                    toUser(user, googleAccount)
                }
            }
        }
    }

    suspend fun getUserByGoogleAccountID(id: Int): User? {
        return withContext(Dispatchers.IO) {
            transaction {
                Users.select { Users.googleAccountID eq id }.firstOrNull()?.let { user ->
                    var googleAccount: GoogleAccount? = null
                    user[Users.googleAccountID]?.let {
                        googleAccount =
                            GoogleAccounts.select { GoogleAccounts.id eq it }.map { toGoogleAccount(it) }.firstOrNull()
                    }
                    toUser(user, googleAccount)
                }
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

    fun delete(data: User) {
        transaction {
            Users.deleteWhere { Users.id eq data.id }
        }
    }

    private fun toUser(row: ResultRow, googleAccount: GoogleAccount?): User = User(
        result = "1",
        id = row[Users.id],
        email = row[Users.email],
        password = row[Users.password],
        name = row[Users.name],
        headPortrait = row[Users.headPortrait],
        role = row[Users.role].name,
        verificationCode = row[Users.verificationCode],
        createTime = row[Users.createTime].toString(),
        updateTime = row[Users.updateTime].toString(),
        googleAccount = googleAccount
    )


    private fun toGoogleAccount(row: ResultRow): GoogleAccount = GoogleAccount(
        result = "1",
        id = row[GoogleAccounts.id],
        googleID = row[GoogleAccounts.googleID],
        email = row[GoogleAccounts.email],
        name = row[GoogleAccounts.name],
        pictureURL = row[GoogleAccounts.pictureURL],
        createTime = row[GoogleAccounts.createTime].toString(),
        updateTime = row[GoogleAccounts.updateTime].toString()
    )
}