package com.choisong.bookshelf.model

data class SearchBookMoreData(
    val result: Boolean,
    val data: ArrayList<GeneralResult>,
    val is_end: Boolean
)