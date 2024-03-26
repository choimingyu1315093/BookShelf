package com.choisong.bookshelf.model

data class AddWishBookData(
    val result: Boolean,
    val data: AddWishBookDataResult
)

data class AddWishBookDataResult(
    val book_isbn: String,
    val books: AddWishBooks,
    val users: AddWishUsers,
    val wish_book_idx: String
)

data class AddWishBooks(
    val book_author: String,
    val book_average_rate: Double,
    val book_comments: ArrayList<AddWishComment>?,
    val book_content: String,
    val book_image: String,
    val book_isbn: String,
    val book_name: String,
    val book_publisher: String,
    val book_translator: String
)

data class AddWishUsers(
    val create_date: String,
    val current_latitude: String,
    val current_longitude: String,
    val delete_date: String,
    val fcm_token: String,
    val login_type: String,
    val user_email: String,
    val user_id: String,
    val user_idx: Int,
    val user_name: String,
    val user_point: Int
)

data class AddWishComment(
    val book_comment_idx: Int?,
    val comment_rate: Int?,
    val comment_content: String?,
    val create_date: String?,
    val update_date: String?,
    val users: AddWishUser

)

data class AddWishUser(
    val user_idx: Int?,
    val user_name: String?,
    val user_point: Int?
)