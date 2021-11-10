package me.lazy_assedninja.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.lazy_assedninja.po.Comments
import me.lazy_assedninja.po.Stores
import me.lazy_assedninja.po.Users
import me.lazy_assedninja.vo.Comment
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

/**
 * Repository that handles Comment instance.
 */
class CommentRepository {

    fun insert(data: Comment) {
        transaction {
            Comments.insert {
                it[userID] = data.userID
                it[storeID] = data.storeID
                it[star] = data.star
                it[content] = data.content
                it[createTime] = DateTime.now()
            }
            val starAverage =
                Comments.innerJoin(Stores)
                    .slice(Stores.id, Comments.star.avg())
                    .select { Stores.id eq data.storeID }
                    .groupBy(Stores.id)
                    .map {
                        it[Comments.star.avg()]
                    }.singleOrNull()
            starAverage?.let {
                Stores.update({ Stores.id eq data.storeID }) {
                    it[star] = starAverage.toFloat()
                }
            }
        }
    }

    suspend fun getAll(storeID: Int): List<Comment> {
        return withContext(Dispatchers.IO) {
            transaction {
                Comments.innerJoin(Users)
                    .select { Comments.storeID eq storeID }
                    .map {
                        toComment(it)
                    }.toList()
            }
        }
    }

    private fun toComment(row: ResultRow): Comment = Comment(
        id = row[Comments.id].value,
        star = row[Comments.star],
        content = row[Comments.content],
        createTime = row[Comments.createTime].toString("yyyy-MM-dd HH:mm:ss"),

        storeID = row[Comments.storeID],
        userID = row[Users.id].value,
        userName = row[Users.name],
        userPicture = row[Users.headPortrait],
    )
}