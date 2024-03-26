package com.choisong.bookshelf.model

data class IWishBookHaveUserData(
    val result: Boolean,
    val data: ArrayList<IWishBookHaveUserDataResult>
)

data class IWishBookHaveUserDataResult(
    val book_author: String?,
    val book_image: String?,
    val book_isbn: String?,
    val book_name: String?,
    val book_publisher: String?,
    val book_translator: String?,
    val current_latitude: String?,
    val current_longitude: String?,
    val distance: Double?,
    val have_book_idx: Int?,
    val user_idx: Int?,
    val user_name: String?,
    val user_point: Int?
)