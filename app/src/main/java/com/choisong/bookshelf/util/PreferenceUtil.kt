package com.choisong.bookshelf.util

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)

    //loginType
    fun getLoginType(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }
    fun setLoginType(key: String, defValue: String){
        prefs.edit().putString(key, defValue).apply()
    }

    //fmcToken
    fun getFcmToken(key: String, defValue: String): String{
        return prefs.getString(key, defValue).toString()
    }
    fun setFcmToken(key: String, defValue: String){
        prefs.edit().putString(key, defValue).apply()
    }

    //AccessToken
    fun getAccessToken(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }
    fun setAccessToken(key: String, defValue: String){
        prefs.edit().putString(key, defValue).apply()
    }

    //UserIdx
    fun getUserIdx(key: String, defValue: Int): Int {
        return prefs.getInt(key, defValue)
    }
    fun setUserIdx(key: String, defValue: Int){
        prefs.edit().putInt(key, defValue).apply()
    }

    //KakaoToken
    fun getKakaoToken(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }
    fun setKakaoToken(key: String, defValue: String){
        prefs.edit().putString(key, defValue).apply()
    }

    //GoogleToken
    fun getGoogleToken(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }
    fun setGoogleToken(key: String, defValue: String){
        prefs.edit().putString(key, defValue).apply()
    }

    //NaverToken
    fun getNaverToken(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }
    fun setNaverToken(key: String, defValue: String){
        prefs.edit().putString(key, defValue).apply()
    }

    //Nickname
    fun getNickname(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }
    fun setNickname(key: String, defValue: String){
        prefs.edit().putString(key, defValue).apply()
    }

    //Description
    fun getDescription(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }
    fun setDescription(key: String, defValue: String){
        prefs.edit().putString(key, defValue).apply()
    }

    //latitude
    fun getLatitude(key: String, defValue: Float): Float {
        return prefs.getFloat(key, defValue)
    }
    fun setLatitude(key: String, defValue: Float){
        prefs.edit().putFloat(key, defValue).apply()
    }

    //longitude
    fun getLongitude(key: String, defValue: Float): Float {
        return prefs.getFloat(key, defValue)
    }
    fun setLongitude(key: String, defValue: Float){
        prefs.edit().putFloat(key, defValue).apply()
    }

    //bestsellerSearch
    fun getBestsellerSearch(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }
    fun setBestsellerSearch(key: String, defValue: String){
        prefs.edit().putString(key, defValue).apply()
    }

    //bestsellerCountry
    fun getBestsellerCountry(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }
    fun setBestsellerCountry(key: String, defValue: String){
        prefs.edit().putString(key, defValue).apply()
    }

    //bestsellerNum
    fun getBestsellerNum(key: String, defValue: Int): Int {
        return prefs.getInt(key, defValue)
    }
    fun setBestsellerNum(key: String, defValue: Int){
        prefs.edit().putInt(key, defValue).apply()
    }

    //isDetail
    fun getDetail(key: String, defValue: Boolean): Boolean {
        return prefs.getBoolean(key, defValue)
    }
    fun setDetail(key: String, defValue: Boolean){
        prefs.edit().putBoolean(key, defValue).apply()
    }

    //chatAlarm
    fun getChatAlarm(key: String, defValue: Boolean): Boolean {
        return prefs.getBoolean(key, defValue)
    }
    fun setChatAlarm(key: String, defValue: Boolean){
        prefs.edit().putBoolean(key, defValue).apply()
    }

    //marketingAlarm
    fun getMarketingAlarm(key: String, defValue: Boolean): Boolean {
        return prefs.getBoolean(key, defValue)
    }
    fun setMarketingAlarm(key: String, defValue: Boolean){
        prefs.edit().putBoolean(key, defValue).apply()
    }

    //nearAlarm
    fun getNearAlarm(key: String, defValue: Boolean): Boolean {
        return prefs.getBoolean(key, defValue)
    }
    fun setNearAlarm(key: String, defValue: Boolean){
        prefs.edit().putBoolean(key, defValue).apply()
    }

    //chatRequestAlarm
    fun getChatRequestAlarm(key: String, defValue: Boolean): Boolean {
        return prefs.getBoolean(key, defValue)
    }
    fun setChatRequestAlarm(key: String, defValue: Boolean){
        prefs.edit().putBoolean(key, defValue).apply()
    }

    //chatUserIdx
    fun getChatUserIdx(key: String, defValue: Int): Int {
        return prefs.getInt(key, defValue)
    }
    fun setChatUserIdx(key: String, defValue: Int){
        prefs.edit().putInt(key, defValue).apply()
    }

    //ID
    fun getId(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }
    fun setId(key: String, defValue: String){
        prefs.edit().putString(key, defValue).apply()
    }

    //Email
    fun getEmail(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }
    fun setEmail(key: String, defValue: String){
        prefs.edit().putString(key, defValue).apply()
    }
}