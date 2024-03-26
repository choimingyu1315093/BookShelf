package com.choisong.bookshelf.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.choisong.bookshelf.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object {
        const val TAG = "MainActivity"
    }

//    lateinit var gso: GoogleSignInOptions
//    lateinit var gsc: GoogleSignInClient
//    lateinit var kakaoCallback: (OAuthToken?, Throwable?) -> Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        bindViews()
    }

    private fun init() = with(binding){
//        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
//        gsc = GoogleSignIn.getClient(this@MainActivity, gso)
//
//        val acct = GoogleSignIn.getLastSignedInAccount(this@MainActivity)
//        if(acct != null){
//            tvNick.text = acct.displayName
//        }
//
//        kakaoCallback = { token, error ->
//            if (error != null) {
//                when {
//                    error.toString() == AuthErrorCause.AccessDenied.toString() -> {
//                        Log.d("[카카오로그인]","접근이 거부 됨(동의 취소)")
//                    }
//                    error.toString() == AuthErrorCause.InvalidClient.toString() -> {
//                        Log.d("[카카오로그인]","유효하지 않은 앱")
//                    }
//                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
//                        Log.d("[카카오로그인]","인증 수단이 유효하지 않아 인증할 수 없는 상태")
//                    }
//                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
//                        Log.d("[카카오로그인]","요청 파라미터 오류")
//                    }
//                    error.toString() == AuthErrorCause.InvalidScope.toString() -> {
//                        Log.d("[카카오로그인]","유효하지 않은 scope ID")
//                    }
//                    error.toString() == AuthErrorCause.Misconfigured.toString() -> {
//                        Log.d("[카카오로그인]","설정이 올바르지 않음(android key hash)")
//                    }
//                    error.toString() == AuthErrorCause.ServerError.toString() -> {
//                        Log.d("[카카오로그인]","서버 내부 에러")
//                    }
//                    error.toString() == AuthErrorCause.Unauthorized.toString() -> {
//                        Log.d("[카카오로그인]","앱이 요청 권한이 없음")
//                    }
//                    else -> { // Unknown
//                        Log.d("[카카오로그인]","기타 에러")
//                    }
//                }
//            }
//            else if (token != null) {
//                Log.d("[카카오로그인]","로그인에 성공하였습니다.\n${token.accessToken}")
//                UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
//                    UserApiClient.instance.me { user, error ->
//                        Log.d(TAG, "카카오닉네임 ${user?.kakaoAccount?.profile?.nickname}")
//                    }
//                }
//            }
//            else {
//                Log.d("카카오로그인", "토큰==null error==null")
//            }
//        }
    }

    private fun bindViews() = with(binding){
//        tvNick.setOnClickListener {
//            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
//            if(UserApiClient.instance.isKakaoTalkLoginAvailable(this@MainActivity)){
//                UserApiClient.instance.loginWithKakaoTalk(this@MainActivity, callback = kakaoCallback)
//            }else{
//                UserApiClient.instance.loginWithKakaoAccount(this@MainActivity, callback = kakaoCallback)
//            }
//        }
    }
}