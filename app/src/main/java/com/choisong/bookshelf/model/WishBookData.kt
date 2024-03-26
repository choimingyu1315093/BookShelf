package com.choisong.bookshelf.model

data class WishBookData(
    val result: Boolean,
    val data: ArrayList<WishBookDataResult>?
)

data class WishBookDataResult(
    val books: WishBooks,
    val wish_book_idx: Int
)

data class WishBooks(
    val book_author: String,
    val book_image: String,
    val book_isbn: String,
    val book_name: String,
    val book_publisher: String,
    val book_translator: String
)