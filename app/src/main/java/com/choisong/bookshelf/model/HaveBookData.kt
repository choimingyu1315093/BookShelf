package com.choisong.bookshelf.model

data class HaveBookData(
    val result: Boolean,
    val data: ArrayList<HaveBookDataResult>?
)

data class HaveBookDataResult(
    val books: HaveBooks,
    val have_book_idx: Int
)

data class HaveBooks(
    val book_author: String,
    val book_image: String,
    val book_isbn: String,
    val book_name: String,
    val book_publisher: String,
    val book_translator: String
)