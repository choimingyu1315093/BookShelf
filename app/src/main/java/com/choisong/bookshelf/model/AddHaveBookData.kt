package com.choisong.bookshelf.model

data class AddHaveBookData(
    val result: Boolean,
    val data: AddHaveBookDataResult
)

data class AddHaveBookDataResult(
    val book_isbn: String,
    val books: AddHaveBooks,
    val have_book_idx: String,
    val users: AddHaveUsers
)

data class AddHaveBooks(
    val book_author: String,
    val book_average_rate: Double,
    val book_comments: ArrayList<AddHaveComment>?,
    val book_content: String,
    val book_image: String,
    val book_isbn: String,
    val book_name: String,
    val book_publisher: String,
    val book_translator: String
)

data class AddHaveUsers(
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

data class AddHaveComment(
    val book_comment_idx: Int?,
    val comment_rate: Int?,
    val comment_content: String?,
    val create_date: String?,
    val update_date: String?,
    val users: AddHaveUser

)

data class AddHaveUser(
    val user_idx: Int?,
    val user_name: String?,
    val user_point: Int?
)