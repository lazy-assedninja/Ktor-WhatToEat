package me.lazy_assedninja.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.lazy_assedninja.db.Tags
import me.lazy_assedninja.dto.Tag
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

class TagRepository {
    fun insert(data: Tag) {
        transaction {
            Tags.insert {
                it[name] = data.name
                it[createTime] = DateTime.now()
                it[updateTime] = DateTime.now()
            }
        }
    }

    suspend fun get(id: Int): Tag? {
        return withContext(Dispatchers.IO) {
            transaction {
                Tags.select { Tags.id eq id }.map {
                    toTag(it)
                }.firstOrNull()
            }
        }
    }

    private fun toTag(row: ResultRow): Tag = Tag(
        id = row[Tags.id],
        name = row[Tags.name],
    )
}