package com.choisong.bookshelf.model

data class SearchBookMoreModel(
    val popular_result_list: ArrayList<String>,
    val page: Int,
    val book_name: String,
    val book_author: String
)
