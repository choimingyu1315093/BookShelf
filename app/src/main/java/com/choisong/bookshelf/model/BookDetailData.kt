package com.choisong.bookshelf.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BookDetailData(
    val result: Boolean,
    val data: BookDetailDataResult
): Parcelable

@Parcelize
data class BookDetailDataResult(
    val book_isbn: String,
    val book_name: String,
    val book_content: String,
    val book_author: String,
    val book_publisher: String,
    val book_image: String,
    val book_full_page: Int,
    val is_have_book: String,
    val read_type: String,
    val read_start_date: String,
    val read_end_date: String,
    val read_page: Int,
    val edit_full_page: Boolean,
    val my_book_idx: Int,
    val book_translator: String,
    val book_average_rate: Double,
    val book_comments: ArrayList<BookDetailComment>?
): Parcelable

@Parcelize
data class BookDetailComment(
    val book_comment_idx: Int,
    val comment_content: String,
    val comment_rate: Double,
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