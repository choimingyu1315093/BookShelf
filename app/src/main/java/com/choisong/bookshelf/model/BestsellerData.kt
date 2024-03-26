package com.choisong.bookshelf.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class BestsellerData(
    val item: List<BestsellerResultData>,
    val itemsPerPage: Int,
    val link: String,
    val logo: String,
    val pubDate: String,
    val query: String,
    val searchCategoryId: Int,
    val searchCategoryName: String,
    val startIndex: Int,
    val title: String,
    val totalResults: Int,
    val version: String
): Parcelable

@Parcelize
data class BestsellerResultData(
    val adult: Boolean?,
    val author: String?,
    val bestRank: Int?,
    val categoryId: Int?,
    val categoryName: String?,
    val cover: String?,
    val customerReviewRank: Int?,
    val description: String?,
    val fixedPrice: Boolean?,
    val isbn: String?,
    val isbn13: String?,
    val itemId: Int?,
    val link: String?,
    val mallType: String?,
    val mileage: Int?,
    val priceSales: Int?,
    val priceStandard: Int?,
    val pubDate: String?,
    val publisher: String?,
    val salesPoint: Int?,
    val seriesInfo: SeriesInfo?,
    val stockStatus: String?,
    val title: String?
): Parcelable

@Parcelize
data class SeriesInfo(
    val seriesId: Int?,
    val seriesLink: String?,
    val seriesName: String?
): Parcelable
