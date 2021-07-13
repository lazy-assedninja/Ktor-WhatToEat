package me.lazy_assedninja.repository

import me.lazy_assedninja.db.GoogleAccounts
import me.lazy_assedninja.db.Users
import me.lazy_assedninja.dto.GoogleAccount
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.lazy_assedninja.dto.request.GoogleAccountRequest
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

@Suppress("unused")
class GoogleAccountRepository {
    fun bind(userID: Int, data: GoogleAccountRequest) {
        transaction {
            GoogleAccounts.insert {
                it[googleID] = data.googleID
                it[email] = data.email
                it[name] = data.name
                it[pictureURL] = data.pictureURL
                it[createTime] = DateTime.now()
                it[updateTime] = DateTime.now()
            }
            val googleAccountID = GoogleAccounts.slice(GoogleAccounts.id)
                .select { GoogleAccounts.googleID eq data.googleID }
                .first()[GoogleAccounts.id]
            Users.update({ Users.id eq userID }) {
                it[Users.googleAccountID] = googleAccountID
            }
        }
    }

    suspend fun get(id: Int): GoogleAccount? {
        return withContext(Dispatchers.IO) {
            transaction {
                GoogleAccounts.select { GoogleAccounts.id eq id }.firstOrNull()?.let {
                    toGoogleAccount(it)
                }
            }
        }
    }

    suspend fun get(googleID: String): GoogleAccount? {
        return withContext(Dispatchers.IO) {
            transaction {
                GoogleAccounts.select { GoogleAccounts.googleID eq googleID }.firstOrNull()?.let {
                    toGoogleAccount(it)
                }
            }
        }
    }

    suspend fun getAll(): List<GoogleAccount> {
        return withContext(Dispatchers.IO) {
            transaction {
                GoogleAccounts.selectAll().map { toGoogleAccount(it) }.toList()
            }
        }
    }

    fun delete(id: Int) {
        transaction {
            GoogleAccounts.deleteWhere { GoogleAccounts.id eq id }
        }
    }

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