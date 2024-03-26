package com.choisong.bookshelf.model

data class CreateChatroomModel(
    val opponent_user_idx: Int,
    val book_isbn: String
)
