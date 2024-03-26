package com.choisong.bookshelf

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK
import com.choisong.bookshelf.util.PreferenceUtil
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication: Application() {

    companion object {
        lateinit var prefs: PreferenceUtil
    }

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        prefs = PreferenceUtil(applicationContext)

        KakaoSdk.init(this, getString(R.string.KAKAO_NATIVE_KEY))
        NaverIdLoginSDK.initialize(this, getString(R.string.NAVER_CLIENT_ID), getString(R.string.NAVER_CLIENT_SECRET),"책꽂이")
    }
}