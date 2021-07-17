package me.lazy_assedninja.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.lazy_assedninja.db.*
import me.lazy_assedninja.dto.Reservation
import me.lazy_assedninja.dto.request.ReservationRequest
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

class ReservationRepository {
    fun insert(userID: Int, storeID: Int, data: Reservation) {
        transaction {
            Reservations.insert {
                it[name] = data.name
                it[phone] = data.phone
                it[amount] = data.amount
                it[time] = data.time
                it[createTime] = DateTime.now()
                it[updateTime] = DateTime.now()

                it[Reservations.userID] = userID
                it[Reservations.storeID] = storeID
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
        id = row[Reservations.id],
        name = row[Reservations.phone],
        phone = row[Reservations.phone],
        amount = row[Reservations.amount],
        time = row[Reservations.time],
        createTime = row[Reservations.createTime].toString(),
        updateTime = row[Reservations.updateTime].toString(),

        storeName = row[Stores.name],
    )

    private fun toStoreReservation(row: ResultRow): Reservation = Reservation(
        id = row[Reservations.id],
        name = row[Reservations.phone],
        phone = row[Reservations.phone],
        amount = row[Reservations.amount],
        time = row[Reservations.time],
        createTime = row[Reservations.createTime].toString(),
        updateTime = row[Reservations.updateTime].toString(),

        userName = row[Users.name],
    )
}