package me.lazy_assedninja.db

import me.lazy_assedninja.po.*
import me.lazy_assedninja.repository.*
import me.lazy_assedninja.vo.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Database configurations.
 */
class DataBaseFactory(private val db: Database) {

    constructor(url: String, driver: String, user: String, password: String) : this(
        Database.connect(
            url = url,
            driver = driver,
            user = user,
            password = password
        )
    )

    fun init() {
        TransactionManager.defaultDatabase = db

        // Create used tables
        transaction {
            SchemaUtils.create(
                Users,
                GoogleAccounts,
                Stores,
                Tags,
                Favorites,
                Comments,
                Posts,
                Reservations,
                Promotions,
                Reports
            )
            addLogger(StdOutSqlLogger)
        }
    }
}