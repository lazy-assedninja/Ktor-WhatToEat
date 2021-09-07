package me.lazy_assedninja.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.lazy_assedninja.po.Favorites
import me.lazy_assedninja.po.Stores
import me.lazy_assedninja.po.Tags
import me.lazy_assedninja.vo.Favorite
import me.lazy_assedninja.vo.Store
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

class FavoriteRepository {

    fun insert(data: Favorite) {
        transaction {
            Favorites.insert {
                it[userID] = data.userID
                it[storeID] = data.storeID
                it[createTime] = DateTime.now()
                it[updateTime] = DateTime.now()
            }
        }
    }

    suspend fun getAll(userID: Int): List<Store> {
        return withContext(Dispatchers.IO) {
            transaction {
                Stores.leftJoin(Tags)
                    .innerJoin(Favorites)
                    .select { Favorites.userID eq userID }
                    .map { store ->
                        val tag = store[Stores.tagID]?.let {
                            store[Tags.name]
                        }
                        toStore(store, tag)
                    }.toList()
            }
        }
    }

    fun delete(storeID: Int, userID: Int) {
        transaction {
            Favorites.deleteWhere { (Favorites.storeID eq storeID) and (Favorites.userID eq userID) }
        }
    }

    private fun toStore(row: ResultRow, tag: String?): Store = Store(
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
        createTime = row[Stores.createTime].toString("yyyy-MM-dd"),
        updateTime = row[Stores.updateTime].toString("yyyy-MM-dd"),

        tagName = tag,
        isFavorite = true
    )
}