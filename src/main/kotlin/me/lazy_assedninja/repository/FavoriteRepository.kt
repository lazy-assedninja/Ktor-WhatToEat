package me.lazy_assedninja.repository

import me.lazy_assedninja.db.Favorites
import me.lazy_assedninja.dto.Favorite
import org.jetbrains.exposed.sql.insert
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
}