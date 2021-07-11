package me.lazy_assedninja.db

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction

class DataBaseFactory(private val db: Database) {
    constructor(user: String, password: String) : this(
        Database.connect("jdbc:h2:mem:regular;DB_CLOSE_DELAY=-1;", driver = "org.h2.Driver", user = user, password = password)
    )

    fun init() {
        TransactionManager.defaultDatabase = db

        // Create used tables
        transaction {
            SchemaUtils.create(Users, GoogleAccounts)
            addLogger(StdOutSqlLogger)
        }
    }
}