package me.lazy_assedninja.repository

import me.lazy_assedninja.db.Tags
import me.lazy_assedninja.dto.Tag
import org.jetbrains.exposed.sql.insert
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
}