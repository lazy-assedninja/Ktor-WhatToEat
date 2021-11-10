package me.lazy_assedninja.routes

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import me.lazy_assedninja.vo.Comment
import me.lazy_assedninja.vo.Post
import me.lazy_assedninja.dto.CommentDTO
import me.lazy_assedninja.dto.PostDTO
import me.lazy_assedninja.dto.StoreDTO
import me.lazy_assedninja.repository.CommentRepository
import me.lazy_assedninja.repository.PostRepository
import me.lazy_assedninja.repository.StoreRepository

/**
 * Define APIs that related to Store.
 */
@Suppress("SENSELESS_COMPARISON")
fun Route.storeRoute(
    storeRepository: StoreRepository = StoreRepository(),
    commentRepository: CommentRepository = CommentRepository(),
    postRepository: PostRepository = PostRepository()
) {

    route("/Store") {
        post("GetStoreList") {
            val data = call.receive<StoreDTO>()
            val userID = data.userID
            val tagID = data.tagID
            if (userID != null && tagID != null) {
                val stores = storeRepository.getAllWithTag(userID, tagID)
                call.respond(stores)
            } else {
                call.respond(HttpStatusCode.InternalServerError, "Data can't be empty.")
            }
        }

        post("GetAllStores") {
            val data = call.receive<StoreDTO>()
            val userID = data.userID
            if (userID != null) {
                val stores = storeRepository.getAll(userID)
                call.respond(stores)
            } else {
                call.respond(HttpStatusCode.InternalServerError, "Data can't be empty.")
            }
        }

        post("Search") {
            val data = call.receive<StoreDTO>()
            val userID = data.userID
            val keyword = data.keyword
            if (userID != null && keyword != null) {
                val stores = storeRepository.search(userID, "%${keyword}%")
                call.respond(stores)
            } else {
                call.respond(HttpStatusCode.InternalServerError, "Data can't be empty.")
            }
        }

        post("CreateComment") {
            val data = call.receive<Comment>()
            commentRepository.insert(data)
            call.respond(mapOf("result" to "Success."))
        }

        post("GetCommentList") {
            val data = call.receive<CommentDTO>()
            val storeID = data.storeID
            if (storeID != null) {
                val comments = commentRepository.getAll(storeID)
                call.respond(comments)
            } else {
                call.respond(HttpStatusCode.InternalServerError, "Data can't be empty.")
            }
        }

        post("CreatePost") {
            val data = call.receive<Post>()
            postRepository.insert(data)
            call.respond(mapOf("result" to "Success."))
        }

        post("GetPostList") {
            val data = call.receive<PostDTO>()
            val storeID = data.storeID
            if (storeID != null) {
                val posts = postRepository.getAll(storeID)
                call.respond(posts)
            } else {
                call.respond(HttpStatusCode.InternalServerError, "Data can't be empty.")
            }
        }
    }
}
