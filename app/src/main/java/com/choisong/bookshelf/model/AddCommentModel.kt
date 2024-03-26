package com.choisong.bookshelf.model

data class AddCommentModel(
    val book_isbn: String,
    val comment_content: String,
    val comment_rate: Double
)