package com.choisong.bookshelf.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BookDetailData(
    val result: Boolean,
    val data: BookDetailDataResult
): Parcelable

@Parcelize
data class BookDetailDataResult(
    val book_author: String,
    val book_average_rate: Double,
    val book_comments: ArrayList<BookDetailComment>?,
    val book_content: String,
    val book_image: String,
    val book_isbn: String,
    val book_name: String,
    val book_publisher: String,
    val book_translator: String
): Parcelable

@Parcelize
data class BookDetailComment(
    val book_comment_idx: Int,
    val comment_content: String,
    val comment_rate: Int,
    val create_date: String,
    val update_date: String,
    val users: BookDetailUser
): Parcelable

@Parcelize
data class BookDetailUser(
    val user_idx: Int,
    val user_name: String,
    val user_point: Int
): Parcelable