package me.lazy_assedninja.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.lazy_assedninja.db.Reports
import me.lazy_assedninja.db.Stores
import me.lazy_assedninja.db.Users
import me.lazy_assedninja.dto.Report
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

class CustomServiceRepository {
    fun insertReport(data: Report) {
        transaction {
            Reports.insert {
                it[content] = data.content
                it[createTime] = DateTime.now()

                it[storeID] = data.storeID
                it[userID] = data.userID
            }
        }
    }

    suspend fun getAllReports(): List<Report> {
        return withContext(Dispatchers.IO) {
            transaction {
                Reports.innerJoin(Stores)
                    .innerJoin(Users)
                    .selectAll()
                    .map {
                        toReport(it)
                    }.toList()
            }
        }
    }

    private fun toReport(row: ResultRow): Report = Report(
        id = row[Reports.id].value,
        content = row[Reports.content],
        createTime = row[Reports.createTime].toString(),

        storeID = row[Reports.storeID],
        storeName = row[Stores.name],
        userID = row[Reports.userID],
        userName = row[Users.name],
        userPicture = row[Users.headPortrait]
    )
}