package com.choisong.bookshelf.model

data class AddCommentData(
    val result: Boolean,
    val data: AddCommentDataResult
)

data class AddCommentDataResult(
    val book_comment_idx: Int?,
    val books: AddCommentDataBooks?,
    val comment_content: String?,
    val comment_rate: Double?,
    val create_date: String?,
    val delete_date: String?,
    val update_date: String?,
    val users: AddCommentDataUser?
)

data class AddCommentDataBooks(
    val book_author: String?,
    val book_average_rate: Double?,
    val book_comments: ArrayList<BookComment>?,
    val book_content: String?,
    val book_image: String?,
    val book_isbn: String?,
    val book_name: String?,
    val book_publisher: String?,
    val book_translator: String?
)

data class BookComment(
    val book_comment_idx: Int?,
    val comment_content: String?,
    val comment_rate: Double?,
    val create_date: String?,
    val update_date: String?,
    val users: Users?
)

data class AddCommentDataUser(
    val create_date: String?,
    val current_latitude: String?,
    val current_longitude: String?,
    val delete_date: String?,
    val fcm_token: String?,
    val login_type: String?,
    val user_email: String?,
    val user_id: String?,
    val user_idx: Int?,
    val user_name: String?,
    val user_point: Int?
)

data class Users(
    val user_idx: Int,
    val user_name: String,
    val user_point: Int
)

