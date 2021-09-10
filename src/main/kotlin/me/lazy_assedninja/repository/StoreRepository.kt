package me.lazy_assedninja.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.lazy_assedninja.po.Favorites
import me.lazy_assedninja.po.Stores
import me.lazy_assedninja.po.Tags
import me.lazy_assedninja.vo.Store
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

@Suppress("SENSELESS_COMPARISON")
class StoreRepository {

    fun insert(tagID: Int?, data: Store) {
        transaction {
            Stores.insert {
                it[placeID] = data.placeID
                it[name] = data.name
                it[address] = data.address
                it[phone] = data.phone
                it[picture] = data.picture
                it[latitude] = data.latitude
                it[longitude] = data.longitude
                it[website] = data.website
                it[star] = data.star
                it[createTime] = DateTime.now()
                it[updateTime] = DateTime.now()

                it[Stores.tagID] = tagID
            }
        }
    }

    suspend fun getAll(userID: Int, tagID: Int): List<Store> {
        return withContext(Dispatchers.IO) {
            transaction {
                Stores.leftJoin(Tags)
                    .join(
                        Favorites,
                        JoinType.LEFT,
                        Stores.id,
                        Favorites.storeID,
                        additionalConstraint = { Favorites.userID eq userID })
                    .select { Stores.tagID eq tagID }
                    .map { store -> toStore(store, store[Favorites.id] != null) }
                    .toList()
            }
        }
    }

    suspend fun search(userID: Int, keyword: String): List<Store> {
        return withContext(Dispatchers.IO) {
            transaction {
                Stores.leftJoin(Tags)
                    .join(
                        Favorites,
                        JoinType.LEFT,
                        Stores.id,
                        Favorites.storeID,
                        additionalConstraint = { Favorites.userID eq userID })
                    .select { Stores.name like keyword }
                    .map { store -> toStore(store, store[Favorites.id] != null) }
                    .toList()
            }
        }
    }

    private fun toStore(row: ResultRow, isFavorite: Boolean? = false): Store = Store(
        id = row[Stores.id].value,
        placeID = row[Stores.placeID],
        name = row[Stores.name],
        address = row[Stores.address],
        phone = row[Stores.phone],
        picture = row[Stores.picture],
        latitude = row[Stores.latitude],
        longitude = row[Stores.longitude],
        website = row[Stores.website],
        star = row[Stores.star],
        createTime = row[Stores.createTime].toString("yyyy-MM-dd HH:mm:ss"),
        updateTime = row[Stores.updateTime].toString("yyyy-MM-dd HH:mm:ss"),

        isFavorite = isFavorite
    )
}