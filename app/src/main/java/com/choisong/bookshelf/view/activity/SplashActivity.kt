package com.choisong.bookshelf.view.activity

import android.Manifest
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.databinding.ActivitySplashBinding
import com.choisong.bookshelf.model.SignInModel
import com.choisong.bookshelf.model.SnsSignInModel
import com.choisong.bookshelf.view.fragment.login.LoginFragment
import com.choisong.bookshelf.viewmodel.LoginViewModel
import com.google.firebase.messaging.FirebaseMessaging
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val loginViewModel: LoginViewModel by viewModels()

    companion object {
        const val TAG = "SplashActivity"
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestPermission()
        observeViewModel()
    }

    private fun requestPermission(){
        val permissions = mutableListOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions.add(Manifest.permission.POST_NOTIFICATIONS)
        }

        TedPermission.create()
            .setPermissionListener(object : PermissionListener {
                override fun onPermissionGranted() {
                    binding.btnStart.setOnClickListener {
                        getFcmToken()
//                        if(MyApplication.prefs.getAutoLogin("autoLogin", false)){
//                            if(MyApplication.prefs.getLoginType("loginType", "general") == "general"){
//                                val signInModel = SignInModel(MyApplication.prefs.getFcmToken("fcmToken", ""), "general", MyApplication.prefs.getLoginId("loginId", ""), MyApplication.prefs.getLoginPw("loginPw", ""))
//                                loginViewModel.signIn(signInModel)
//                            }else if(MyApplication.prefs.getLoginType("loginType", "general") == "kakao"){
//                                val snsSignInModel = SnsSignInModel(MyApplication.prefs.getFcmToken("fcmToken", ""), "kakao", MyApplication.prefs.getKakaoToken("kakaoToken", ""))
//                                loginViewModel.snsSignIn(snsSignInModel)
//                            }else if(MyApplication.prefs.getLoginType("loginType", "general") == "naver"){
//                                val snsSignInModel = SnsSignInModel(MyApplication.prefs.getFcmToken("fcmToken", ""), "naver", MyApplication.prefs.getNaverToken("naverToken", ""))
//                                loginViewModel.snsSignIn(snsSignInModel)
//                            }else {
//                                val snsSignInModel = SnsSignInModel(MyApplication.prefs.getFcmToken("fcmToken", ""), "google", MyApplication.prefs.getGoogleToken("googleToken", ""))
//                                loginViewModel.snsSignIn(snsSignInModel)
//                            }
//                        }else {
//                            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
//                            startActivity(intent)
//                        }
                        val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                        startActivity(intent)
                    }
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    Log.d(TAG, "거부된 권한들: $deniedPermissions")
                    Toast.makeText(this@SplashActivity, "필요한 권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
                }
            })
            .setDeniedMessage("필요한 권한을 허용해주세요.")
            .setPermissions(*permissions.toTypedArray())
            .check()
    }

    private fun observeViewModel() = with(binding){
//        loginViewModel.signInResult.observe(this@SplashActivity){
//            if(it){
//                MyApplication.prefs.setLoginType("loginType", "general")
//                loginViewModel.myProfile(MyApplication.prefs.getAccessToken("accessToken", ""))
//            }else {
//                Toast.makeText(this@SplashActivity, "입력하신 정보와 일치하는 회원이 없습니다.", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        loginViewModel.snsSignInResult.observe(this@SplashActivity){
//            if(it){
//                val intent = Intent(this@SplashActivity, HomeActivity::class.java)
//                startActivity(intent)
//            }else {
//                Toast.makeText(this@SplashActivity, "입력하신 정보와 일치하는 회원이 없습니다.", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        loginViewModel.myProfileResult.observe(this@SplashActivity){
//            if(it){
//                val intent = Intent(this@SplashActivity, HomeActivity::class.java)
//                startActivity(intent)
//            }
//        }
    }

    private fun getFcmToken(){
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result
                    Log.d(LoginFragment.TAG, "login getFcmToken: $token")
                    MyApplication.prefs.setFcmToken("fcmToken", token)
                } else {
                    Log.e("FCM Token", "Failed to get token: ${task.exception}")
                }
            }
    }
}