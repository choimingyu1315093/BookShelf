package com.choisong.bookshelf.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchBookData(
    val result: Boolean,
    val data: SearchBookResultData
): Parcelable

@Parcelize
data class SearchBookResultData(
    val popular_result: ArrayList<PopularResult>,
    val general_result: ArrayList<GeneralResult>,
    val is_end: Boolean
): Parcelable

@Parcelize
data class PopularResult(
    val book_author: String,
    val book_image: String,
    val book_isbn: String,
    val book_name: String,
    val book_publisher: String,
    val book_translator: String,
    val book_average_rate: Double?,
    val book_comments: ArrayList<SearchBookComment>?,
    val book_content: String,
    val is_have_book: Boolean?,
    val read_type: String?
): Parcelable

@Parcelize
data class GeneralResult(
    val book_author: String,
    val book_image: String,
    val book_isbn: String,
    val book_name: String,
    val book_publisher: String,
    val book_translator: String,
    val book_average_rate: Double?,
    val book_comments: ArrayList<SearchBookComment>?,
    val book_content: String,
    val is_have_book: Boolean?,
    val read_type: String?
): Parcelable

@Parcelize
data class SearchBookComment(
    val book_comment_idx: Int?,
    val comment_rate: Int?,
    val comment_content: String?,
    val create_date: String?,
    val update_date: String?,
    val users: SearchBookUser
): Parcelable

@Parcelize
data class SearchBookUser(
    val user_idx: Int?,
    val user_name: String?,
    val user_point: Int?
): Parcelable