package com.choisong.bookshelf.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class PaymentItem(
    @SerializedName("jsonString")
    val jsonString: String?,
    @SerializedName("parsedJson")
    val parsedJson: ParsedJson?,
    @SerializedName("productDetailsToken")
    val productDetailsToken: String?,
    @SerializedName("productId")
    val productId: String?,
    @SerializedName("productType")
    val productType: String?,
    @SerializedName("subscriptionOfferDetails")
    val subscriptionOfferDetails: Any?,
    @SerializedName("title")
    val title: String?
)

data class ParsedJson(
    @SerializedName("description")
    val description: String?,
    @SerializedName("localizedIn")
    val localizedIn: List<String>?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("oneTimePurchaseOfferDetails")
    val oneTimePurchaseOfferDetails: OneTimePurchaseOfferDetails?,
    @SerializedName("productId")
    val productId: String?,
    @SerializedName("skuDetailsToken")
    val skuDetailsToken: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("type")
    val type: String?
)

data class OneTimePurchaseOfferDetails(
    @SerializedName("formattedPrice")
    val formattedPrice: String?,
    @SerializedName("priceAmountMicros")
    val priceAmountMicros: Long?,
    @SerializedName("priceCurrencyCode")
    val priceCurrencyCode: String?
)

data class Payment(
    @Expose
    @SerializedName("user_idx")
    var userIdx: Int? = null,

    @Expose
    @SerializedName("coupon_idx")
    var couponIdx: Int? = null,

    @Expose
    @SerializedName("goods_idx")
    var goodsIdx: Int? = null,

    //스토어에 올라가는 값인데 일단 아무거나 넣어라(테스트전)
    @Expose
    @SerializedName("payment_id")
    var paymentId: String? = null,

    @Expose
    @SerializedName("disc_amount")
    var discAmount: Int? = null,

    //결제금액
    @Expose
    @SerializedName("amount")
    var amount: Int? = null
)