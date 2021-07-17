package me.lazy_assedninja.db

import me.lazy_assedninja.dto.Favorite
import me.lazy_assedninja.dto.Store
import me.lazy_assedninja.dto.Tag
import me.lazy_assedninja.dto.User
import me.lazy_assedninja.repository.FavoriteRepository
import me.lazy_assedninja.repository.StoreRepository
import me.lazy_assedninja.repository.TagRepository
import me.lazy_assedninja.repository.UserRepository
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction

class DataBaseFactory(private val db: Database) {
    constructor(user: String, password: String) : this(
        Database.connect(
            "jdbc:h2:mem:regular;DB_CLOSE_DELAY=-1;",
            driver = "org.h2.Driver",
            user = user,
            password = password
        )
    )

    fun init() {
        TransactionManager.defaultDatabase = db

        // Create used tables
        transaction {
            SchemaUtils.create(Users, GoogleAccounts, Stores, Tags, Favorites, Comments, Posts, Reservations)
            addLogger(StdOutSqlLogger)
        }
    }

    fun createTestData() {
        val userRepository = UserRepository()
        val storeRepository = StoreRepository()
        val tagRepository = TagRepository()
        val favoriteRepository = FavoriteRepository()

        userRepository.insert(
            User(
                result = null,

                id = 0,
                email = "test1@gmail.com",
                name = "test1",
                password = "000000",
                headPortrait = "picture/path",
                role = "user",
                verificationCode = null,
                createTime = null,
                updateTime = null,

                googleAccount = null
            )
        )
        userRepository.insert(
            User(
                result = null,

                id = 0,
                email = "test2@gmail.com",
                name = "test2",
                password = "000000",
                headPortrait = "picture/path",
                role = "user",
                verificationCode = null,
                createTime = null,
                updateTime = null,

                googleAccount = null
            )
        )
        tagRepository.insert(
            Tag(
                id = 0,
                name = "test1"
            )
        )
        tagRepository.insert(
            Tag(
                id = 0,
                name = "test2"
            )
        )
        storeRepository.insert(
            1,
            Store(
                id = 0,
                placeID = "ChIJh876E3qpQjQRC9mJfNU4rqk",
                name = "火樹銀花韓式燒烤",
                address = "100台灣台北市中正區濟南路二段3之10號",
                phone = "02 2321 2729",
                latitude = "25.0416267",
                longitude = "121.5270648"
            )
        )
        storeRepository.insert(
            1,
            Store(
                id = 0,
                placeID = "ChIJNxcEF3qpQjQRrwkfW0VAyJ4",
                name = "巧之味手工水餃 濟南店",
                address = "100台灣台北市中正區濟南路二段6號",
                phone = "02 2321 4693",
                latitude = "25.0412988",
                longitude = "121.5268078"
            )
        )
        storeRepository.insert(
            1,
            Store(
                id = 0,
                placeID = "ChIJC8ObMXqpQjQRXnbI0Upn_4Y",
                name = "大嗑西式餐館",
                address = "100台灣台北市中正區濟南路二段18-3號",
                phone = "02 2394 8810",
                latitude = "25.040981",
                longitude = "121.528242"
            )
        )
        storeRepository.insert(
            2,
            Store(
                id = 0,
                placeID = "ChIJ_QYiGnupQjQRC8BOTkFJuHs",
                name = "Alleycat's Pizza",
                address = "100台灣台北市中正區八德路一段1號",
                phone = "02 2395 6006",
                latitude = "25.0442106",
                longitude = "121.5293415"
            )
        )
        favoriteRepository.insert(
            Favorite(
                id = 0,
                userID = 1,
                storeID = 1,
            )
        )
        favoriteRepository.insert(
            Favorite(
                id = 0,
                userID = 1,
                storeID = 4,
            )
        )
        favoriteRepository.insert(
            Favorite(
                id = 0,
                userID = 2,
                storeID = 2,
            )
        )
    }
}