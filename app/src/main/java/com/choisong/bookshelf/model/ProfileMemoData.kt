package com.choisong.bookshelf.model

data class ProfileMemoData(
    val result: Boolean,
    val data: ArrayList<ProfileMemoDataResult>
)

data class ProfileMemoDataResult(
    val books: ProfileMemoBook,
    val create_date: String,
    val is_public: String,
    val memo_content: String,
    val memo_idx: Int
)

data class ProfileMemoBook(
    val book_isbn: String,
    val book_name: String
)