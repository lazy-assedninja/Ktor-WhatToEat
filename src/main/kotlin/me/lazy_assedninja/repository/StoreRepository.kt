package me.lazy_assedninja.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.lazy_assedninja.db.Favorites
import me.lazy_assedninja.db.Stores
import me.lazy_assedninja.db.Tags
import me.lazy_assedninja.dto.Store
import me.lazy_assedninja.dto.Tag
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

@Suppress("unused", "SENSELESS_COMPARISON")
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
                    .map { store ->
                        val tag = store[Stores.tagID]?.let {
                            toTag(store)
                        }
                        toStore(store, tag, store[Favorites.id] != null)
                    }.toList()
            }
        }
    }

    suspend fun search(userID: Int, keyword: String): List<Store> {
        return withContext(Dispatchers.IO) {
            transaction {
                Stores.join(
                    Favorites,
                    JoinType.LEFT,
                    Stores.id,
                    Favorites.storeID,
                    additionalConstraint = { Favorites.userID eq userID })
                    .select { Stores.name like keyword }
                    .map { store ->
                        toStore(store, null, store[Favorites.id] != null)
                    }.toList()
            }
        }
    }

    private fun toStore(row: ResultRow, tag: Tag?, isFavorite: Boolean? = false): Store = Store(
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

        tag = tag,
        isFavorite = isFavorite
    )

    private fun toTag(row: ResultRow): Tag = Tag(
        id = row[Tags.id],
        name = row[Tags.name],
    )
}