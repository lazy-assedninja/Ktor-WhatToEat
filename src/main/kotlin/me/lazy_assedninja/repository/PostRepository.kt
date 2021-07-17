package me.lazy_assedninja.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.lazy_assedninja.db.Comments
import me.lazy_assedninja.db.Posts
import me.lazy_assedninja.db.Users
import me.lazy_assedninja.dto.Post
import me.lazy_assedninja.dto.request.PostRequest
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.joda.time.DateTime

class PostRepository {
    fun insert(data: PostRequest) {
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
        id = row[Posts.id],
        title = row[Posts.title],
        content = row[Posts.content],
        createTime = row[Posts.createTime].toString()
    )
}