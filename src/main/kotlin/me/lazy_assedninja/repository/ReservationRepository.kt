package me.lazy_assedninja.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.lazy_assedninja.po.Reservations
import me.lazy_assedninja.po.Stores
import me.lazy_assedninja.po.Users
import me.lazy_assedninja.vo.Reservation
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

/**
 * Repository that handles Reservation instance.
 */
class ReservationRepository {

    fun insert(data: Reservation) {
        transaction {
            Reservations.insert {
                it[name] = data.name
                it[phone] = data.phone
                it[amount] = data.amount
                it[time] = data.time
                it[createTime] = DateTime.now()
                it[updateTime] = DateTime.now()

                it[userID] = data.userID
                it[storeID] = data.storeID
            }
        }
    }

    suspend fun getAll(type: String, id: Int): List<Reservation> {
        return withContext(Dispatchers.IO) {
            transaction {
                if (type == "user") {
                    Reservations.innerJoin(Stores)
                        .select { Reservations.userID eq id }
                        .map {
                            toUserReservation(it)
                        }.toList()
                } else {
                    Reservations.innerJoin(Users)
                        .select { Reservations.storeID eq id }
                        .map {
                            toStoreReservation(it)
                        }.toList()
                }
            }
        }
    }

    fun delete(id: Int) {
        transaction {
            Reservations.deleteWhere { Reservations.id eq id }
        }
    }

    private fun toUserReservation(row: ResultRow): Reservation = Reservation(
        id = row[Reservations.id].value,
        name = row[Reservations.name],
        phone = row[Reservations.phone],
        amount = row[Reservations.amount],
        time = row[Reservations.time],
        createTime = row[Reservations.createTime].toString("yyyy-MM-dd HH:mm:ss"),
        updateTime = row[Reservations.updateTime].toString("yyyy-MM-dd HH:mm:ss"),

        storeID = row[Reservations.storeID],
        storeName = row[Stores.name],
        userID = row[Reservations.userID]
    )

    private fun toStoreReservation(row: ResultRow): Reservation = Reservation(
        id = row[Reservations.id].value,
        name = row[Reservations.name],
        phone = row[Reservations.phone],
        amount = row[Reservations.amount],
        time = row[Reservations.time],
        createTime = row[Reservations.createTime].toString("yyyy-MM-dd HH:mm:ss"),
        updateTime = row[Reservations.updateTime].toString("yyyy-MM-dd HH:mm:ss"),

        storeID = row[Reservations.storeID],
        userID = row[Reservations.userID],
        userName = row[Users.name]
    )
}