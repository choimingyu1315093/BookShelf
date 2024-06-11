package com.choisong.bookshelf.model

data class IWishBookHaveUserData(
    val result: Boolean,
    val data: ArrayList<IWishBookHaveUserDataResult>
)

data class IWishBookHaveUserDataResult(
    val my_book_idx: Int,
    val book_name: String?,
    val book_author: String?,
    val book_translator: String?,
    val book_publisher: String?,
    val book_image: String?,
    val book_isbn: String?,
    val user_idx: Int?,
    val user_name: String?,
    val user_point: Int?,
    val current_latitude: String?,
    val current_longitude: String?,
    val distance: Double?,
)
