package me.lazy_assedninja.repository

import me.lazy_assedninja.po.Tags
import me.lazy_assedninja.vo.Tag
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