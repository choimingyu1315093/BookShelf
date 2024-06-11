package com.choisong.bookshelf.model

data class AddMyBookData(
    val result: Boolean,
    val data: AddMyBookDataResult
)

data class AddMyBookDataResult(
    val book_full_page: Int,
    val book_isbn: String,
    val books: AddMyBookDataBook,
    val is_have_book: String,
    val my_book_idx: String,
    val read_end_date: String?,
    val read_page: Int,
    val read_start_date: String?,
    val read_type: String,
    val users: AddMyBookUser
)

data class AddMyBookDataBook(
    val book_author: String,
    val book_average_rate: Double,
    val book_comments: List<AddMyBookComment>,
    val book_content: String,
    val book_full_page: Int,
    val book_image: String,
    val book_isbn: String,
    val book_name: String,
    val book_publisher: String,
    val book_translator: String,
    val edit_full_page: Boolean,
    val is_have_book: Boolean,
    val read_end_date: String,
    val read_page: Int,
    val read_start_date: String,
    val read_type: String
)

data class AddMyBookUser(
    val create_date: String,
    val current_latitude: String,
    val current_longitude: String,
    val delete_date: String?,
    val fcm_token: String,
    val login_type: String,
    val setting_chat_alarm: Int,
    val setting_chat_receive: Int,
    val setting_marketing_alarm: Int,
    val setting_wish_book_alarm: Int,
    val ticket_count: Int,
    val user_description: String,
    val user_email: String,
    val user_id: String,
    val user_idx: Int,
    val user_name: String,
    val user_point: Int
)

data class AddMyBookComment(
    val book_comment_idx: Int,
    val comment_content: String,
    val comment_rate: Double,
    val create_date: String,
    val update_date: String,
    val users: Users
)