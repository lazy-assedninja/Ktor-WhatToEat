package me.lazy_assedninja.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.lazy_assedninja.db.Comments
import me.lazy_assedninja.db.Stores
import me.lazy_assedninja.db.Users
import me.lazy_assedninja.dto.Comment
import me.lazy_assedninja.dto.request.CommentRequest
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

class CommentRepository {
    fun insert(data: CommentRequest) {
        transaction {
            Comments.insert {
                it[userID] = data.userID
                it[storeID] = data.storeID
                it[star] = data.star
                it[content] = data.content
                it[createTime] = DateTime.now()
            }
            val starAverage =
                Comments.innerJoin(Stores).slice(Stores.id, Comments.star.avg())
                    .select { Stores.id eq data.storeID }.groupBy(Stores.id).map {
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
        id = row[Comments.id],
        userName = row[Users.name],
        userPicture = row[Users.headPortrait],
        star = row[Comments.star],
        content = row[Comments.content],
        createTime = row[Comments.createTime].toString()
    )
}