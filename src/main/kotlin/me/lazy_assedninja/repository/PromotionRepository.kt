package me.lazy_assedninja.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.lazy_assedninja.po.Promotions
import me.lazy_assedninja.po.Stores
import me.lazy_assedninja.vo.Promotion
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

/**
 * Repository that handles Promotion instance.
 */
class PromotionRepository {

    private val formatter = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm")

    fun insert(data: Promotion) {
        transaction {
            Promotions.insert {
                it[title] = data.title
                it[picture] = data.picture
                it[discount] = data.discount
                it[notice] = data.notice
                it[deadline] = formatter.parseDateTime(data.deadline)
                it[createTime] = DateTime.now()

                it[storeID] = data.storeID
            }
        }
    }

    suspend fun getAll(): List<Promotion> {
        return withContext(Dispatchers.IO) {
            transaction {
                Promotions.innerJoin(Stores)
                    .selectAll()
                    .map {
                        toPromotion(it)
                    }.toList()
            }
        }
    }

    private fun toPromotion(row: ResultRow): Promotion = Promotion(
        id = row[Promotions.id].value,
        title = row[Promotions.title],
        picture = row[Promotions.picture],
        discount = row[Promotions.discount],
        notice = row[Promotions.notice],
        deadline = row[Promotions.deadline].toString("yyyy-MM-dd HH"),

        storeID = row[Promotions.storeID],
        storeName = row[Stores.name]
    )
}