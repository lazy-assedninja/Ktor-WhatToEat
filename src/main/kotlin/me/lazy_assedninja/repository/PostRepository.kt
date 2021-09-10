package me.lazy_assedninja.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.lazy_assedninja.po.Posts
import me.lazy_assedninja.vo.Post
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

class PostRepository {

    fun insert(data: Post) {
        transaction {
            Posts.insert {
                it[storeID] = data.storeID
                it[title] = data.title
                it[content] = data.content
                it[createTime] = DateTime.now()
                it[updateTime] = DateTime.now()
            }
        }
    }

    suspend fun getAll(storeID: Int): List<Post> {
        return withContext(Dispatchers.IO) {
            transaction {
                Posts.select { Posts.storeID eq storeID }
                    .map {
                        toPost(it)
                    }.toList()
            }
        }
    }

    private fun toPost(row: ResultRow): Post = Post(
        id = row[Posts.id].value,
        title = row[Posts.title],
        content = row[Posts.content],
        createTime = row[Posts.createTime].toString("yyyy-MM-dd HH:mm:ss"),

        storeID = row[Posts.storeID]
    )
}