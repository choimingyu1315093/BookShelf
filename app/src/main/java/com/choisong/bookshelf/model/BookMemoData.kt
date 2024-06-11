package com.choisong.bookshelf.model

data class BookMemoData(
    val result: Boolean,
    val data: ArrayList<BookMemoDataResult>
)

data class BookMemoDataResult(
    val create_date: String,
    val is_public: String,
    val memo_content: String,
    val memo_idx: Int,
    val users: BookMemoUser
)

data class BookMemoUser(
    val user_idx: Int,
    val user_name: String
)