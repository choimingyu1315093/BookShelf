package com.choisong.bookshelf.model

data class AddBook(
    val result: Boolean,
    val data: AddBookData
)

data class AddBookData(
    val result: AddBookDataResult
)

data class AddBookDataResult(
    val affected: Int,
    val generatedMaps: List<Any>,
    val raw: List<Any>
)