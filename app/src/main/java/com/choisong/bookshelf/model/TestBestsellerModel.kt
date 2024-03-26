package com.choisong.bookshelf.model

data class TestBestsellerModel(
    val idx: Int,
    val img: String,
    val title: String,
    val writer: String,
    val publisher: String,
    val rate: Double,
    val reading: Boolean,
    val wantBook: Boolean,
    val lat: Double,
    val lng: Double
)
