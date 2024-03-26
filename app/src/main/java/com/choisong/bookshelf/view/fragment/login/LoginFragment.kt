package com.choisong.bookshelf.view.fragment.login

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.FragmentLoginBinding
import com.choisong.bookshelf.model.SignInModel
import com.choisong.bookshelf.model.SignUpModel
import com.choisong.bookshelf.model.SnsSignInModel
import com.choisong.bookshelf.model.SnsSignUpModel
import com.choisong.bookshelf.view.activity.HomeActivity
import com.choisong.bookshelf.view.activity.LoginActivity
import com.choisong.bookshelf.view.activity.SignUpActivity
import com.choisong.bookshelf.viewmodel.LoginViewModel
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.acos

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel: LoginViewModel by viewModels()

    companion object {
        const val TAG = "LoginFragment"
        const val REQ_ONE_TAP = 2
        const val showOneTapUI = true
    }

    lateinit var oneTapClient: SignInClient
    lateinit var signUpRequest: BeginSignInRequest
    private var locationManager: LocationManager? = null

    private lateinit var auth: FirebaseAuth

    private var goLogin = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getLocation()
        getFcmToken()
        init()
        bindViews()
        observeViewModel()
    }

    private fun googleLoginSetting(){
        oneTapClient = Identity.getSignInClient(requireActivity())
        signUpRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(getString(R.string.GOOGLE_CLIENT_ID))
                .setFilterByAuthorizedAccounts(false)
                .build())
            .build()
    }

    private fun getLocation() = with(binding){
        locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        var userLocation = getLatLng()
        Log.d(TAG, "getLocation: $userLocation")
        MyApplication.prefs.setLatitude("lat", userLocation.latitude.toFloat())
        MyApplication.prefs.setLongitude("lng", userLocation.longitude.toFloat())
    }
    
    private fun getLatLng(): Location = with(binding){
        var currentLatLng: Location? = null
        if(ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            getLatLng()
        }else {
            val locationProvider = LocationManager.GPS_PROVIDER
            currentLatLng = locationManager?.getLastKnownLocation(locationProvider)
        }
        return currentLatLng!!
    }

    private fun getFcmToken(){
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result
                    MyApplication.prefs.setFcmToken("fcmToken", token)
                } else {
                    Log.e("FCM Token", "Failed to get token: ${task.exception}")
                }
            }
    }

    private fun init() = with(binding){
        cl.setOnClickListener {
            val inputManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(etId.windowToken, 0)
        }

        etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val passwordPattern = Regex("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!]).{8,15}$")
                if(s.toString().isNotEmpty()){
                    if(passwordPattern.matches(s.toString())){
//                        txtWarning.visibility = View.GONE
                    }else {
//                        txtWarning.visibility = View.VISIBLE
                    }
                }else {
//                    txtWarning.visibility = View.GONE
                }
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })
    }

    private fun bindViews() = with(binding){
        ivGoogle.setOnClickListener {
            //TODO: 해당 주석부분을 삭제하지 않고 살려둔 이유는 이게 멋있어 보여서 위에 googleLoginSetting() 까지 삭제하지 않음!! 나중에 해봐라
//            Log.d(TAG, "bindViews: click")
//            oneTapClient.beginSignIn(signUpRequest)
//                .addOnSuccessListener(requireActivity()) { result ->
//                    try {
//                        startIntentSenderForResult(
//                            result.pendingIntent.intentSender, REQ_ONE_TAP,
//                            null, 0, 0, 0, null)
//                    } catch (e: IntentSender.SendIntentException) {
//                        Log.e(TAG, "Couldn't start One Tap UI: ${e.localizedMessage}")
//                    }
//                }
//                .addOnFailureListener(requireActivity()) { e ->
//                    // No Google Accounts found. Just continue presenting the signed-out UI.
//                    Log.d(TAG, e.localizedMessage)
//                }
            auth = FirebaseAuth.getInstance()

            // Google 로그인 옵션 설정
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.GOOGLE_CLIENT_ID))
                .requestEmail()
                .build()

            val googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, 123)
        }

        ivKakao.setOnClickListener {
            UserApiClient.instance.loginWithKakaoAccount(requireContext()) { token, error ->
                if (error != null) {
                    Toast.makeText(requireContext(), "카카오 로그인에 실패 하셨습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    val authorizationCode: String = token!!.accessToken
                    UserApiClient.instance.me { user, error ->
                        if (error != null) {
                            Log.d(TAG, "kakaoLogin: 사용자 정보 가져오기 실패")
                        } else if (user != null) {
                            val nickname = user.kakaoAccount?.profile?.nickname
                            val email = user.kakaoAccount?.email
                            MyApplication.prefs.setNickname("nickname", nickname ?: "Nickname")
                            if(MyApplication.prefs.getKakaoToken("kakaoToken", "") == ""){
                                Log.d(TAG, "bindViews: 카카오 회원가입 후 로그인")
                                MyApplication.prefs.setKakaoToken("kakaoToken", authorizationCode)
                                val snsSignUpModel = SnsSignUpModel("", "kakao", email, MyApplication.prefs.getKakaoToken("kakaoToken", ""), nickname)
                                loginViewModel.snsSignUp(snsSignUpModel)
                            }else {
                                Log.d(TAG, "bindViews: 카카오 로그인")
                                val snsSignInModel = SnsSignInModel("", "kakao", MyApplication.prefs.getKakaoToken("kakaoToken", ""))
                                loginViewModel.snsSignIn(snsSignInModel)
                            }
                            MyApplication.prefs.setLoginType("loginType", "kakao")
                        }
                    }
                }
            }
        }

        ivNaver.setOnClickListener {
            val oauthLoginCallback = object : OAuthLoginCallback {
                override fun onSuccess() {
                    Log.d(TAG, "onSuccess: 네이버 로그인 성공")

                    NidOAuthLogin().callProfileApi(object : NidProfileCallback<NidProfileResponse> {
                        override fun onSuccess(result: NidProfileResponse) {
                            val nickname = result.profile?.nickname
                            val email = result.profile?.email
                            MyApplication.prefs.setNickname("nickname", nickname ?: "Nickname")
                            if(MyApplication.prefs.getNaverToken("naverToken","")  == ""){
                                Log.d(TAG, "bindViews: 네이버 회원가입 후 로그인")
                                MyApplication.prefs.setNaverToken("naverToken", NaverIdLoginSDK.getAccessToken()!!)
                                val snsSignUpModel = SnsSignUpModel("", "naver", email, MyApplication.prefs.getNaverToken("naverToken", ""), nickname)
                                loginViewModel.snsSignUp(snsSignUpModel)
                            }else {
                                Log.d(TAG, "bindViews: 네이버 로그인")
                                val snsSignInModel = SnsSignInModel("", "naver", MyApplication.prefs.getNaverToken("naverToken", ""))
                                loginViewModel.snsSignIn(snsSignInModel)
                            }
                            MyApplication.prefs.setLoginType("loginType", "naver")
                        }

                        override fun onError(errorCode: Int, message: String) {
                            //
                        }

                        override fun onFailure(httpStatus: Int, message: String) {
                            //
                        }
                    })
                }
                override fun onFailure(httpStatus: Int, message: String) {
                    val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                    val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                    Toast.makeText(requireContext(), "errorCode: $errorCode, errorDesc: $errorDescription", Toast.LENGTH_SHORT).show()
                }
                override fun onError(errorCode: Int, message: String) {
                    onFailure(errorCode, message)
                }
            }
            NaverIdLoginSDK.authenticate(requireContext(), oauthLoginCallback)
        }

        txtForget.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_loginFragment_to_passwordFragment)
        }

        btnSignUp.setOnClickListener {
            val intent = Intent(requireContext(), SignUpActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            goLogin = true
            val signInModel = SignInModel(MyApplication.prefs.getFcmToken("fcmToken", ""), "general", etId.text.toString(), etPassword.text.toString())
            loginViewModel.signIn(signInModel)
        }
    }

    private fun observeViewModel() = with(binding){
        loginViewModel.signInResult.observe(viewLifecycleOwner){
            if(goLogin){
                if(it){
                    MyApplication.prefs.setLoginType("loginType", "general")
                    loginViewModel.myProfile(MyApplication.prefs.getAccessToken("accessToken", ""))
                }else {
                    Toast.makeText(requireContext(), "입력하신 정보와 일치하는 회원이 없습니다.", Toast.LENGTH_SHORT).show()
                }
                goLogin = false
            }
        }

        loginViewModel.snsSignInResult.observe(viewLifecycleOwner){
            if(it){
                val intent = Intent(requireContext(), HomeActivity::class.java)
                startActivity(intent)
                (requireActivity() as LoginActivity).finish()
            }else {
                Toast.makeText(requireContext(), "입력하신 정보와 일치하는 회원이 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        loginViewModel.myProfileResult.observe(viewLifecycleOwner){
            if(it){
                val intent = Intent(requireContext(), HomeActivity::class.java)
                startActivity(intent)
                (requireActivity() as LoginActivity).finish()
            }
        }

        loginViewModel.snsSignUpResult.observe(viewLifecycleOwner){
            if(it){
                val intent = Intent(requireContext(), HomeActivity::class.java)
                startActivity(intent)
                (requireActivity() as LoginActivity).finish()
            }else {
                Toast.makeText(requireContext(), "이미 존재하는 사용자입니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

//        when (requestCode) {
//            REQ_ONE_TAP -> {
//                try {
//                    val credential = oneTapClient.getSignInCredentialFromIntent(data)
//                    val idToken = credential.googleIdToken
//                    when {
//                        idToken != null -> {
//                            Log.d(TAG, "Got ID token.$idToken, ${credential.id}, ${credential.displayName}")
////                            if(credential.displayName != null){
////                                MyApplication.prefs.setNickname("nickname", credential.displayName!!)
////                                val intent = Intent(requireContext(), HomeActivity::class.java)
////                                startActivity(intent)
////                            }
//
//                            val nickname = credential.displayName
//                            val email = credential.id
//                            MyApplication.prefs.setNickname("nickname", nickname ?: "Nickname")
//                            if(MyApplication.prefs.getGoogleToken("googleToken","")  == ""){
//                                Log.d(TAG, "bindViews: 구글 회원가입 후 로그인")
//                                MyApplication.prefs.setGoogleToken("googleToken", NaverIdLoginSDK.getAccessToken()!!)
//                                val snsSignUpModel = SnsSignUpModel("", "google", email, MyApplication.prefs.getNaverToken("googleToken", ""), nickname)
//                                loginViewModel.snsSignUp(snsSignUpModel)
//                            }else {
//                                Log.d(TAG, "bindViews: 구글 로그인")
//                                val snsSignInModel = SnsSignInModel("", "google", MyApplication.prefs.getNaverToken("googleToken", ""))
//                                loginViewModel.snsSignIn(snsSignInModel)
//                            }
//                            MyApplication.prefs.setLoginType("loginType", "google")
//                        }
//                        else -> {
//                            Log.d(TAG, "No ID token!")
//                        }
//                    }
//                } catch (e: ApiException) {
//                    Log.d(TAG, "onActivityResult: $e")
//                }
//            }
//        }


        if (requestCode == 123) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google 로그인 성공 시
                val account = task.getResult(ApiException::class.java)!!
                val nickname = account.displayName
                val email = account.email

                MyApplication.prefs.setNickname("nickname", nickname ?: "Nickname")
                if(MyApplication.prefs.getGoogleToken("googleToken","")  == ""){
                    Log.d(TAG, "bindViews: 구글 회원가입 후 로그인")
                    MyApplication.prefs.setGoogleToken("googleToken", account.idToken!!)
                    val snsSignUpModel = SnsSignUpModel("", "google", email, MyApplication.prefs.getGoogleToken("googleToken", ""), nickname)
                    loginViewModel.snsSignUp(snsSignUpModel)
                }else {
                    Log.d(TAG, "bindViews: 구글 로그인")
                    val snsSignInModel = SnsSignInModel("", "google", MyApplication.prefs.getGoogleToken("googleToken", ""))
                    loginViewModel.snsSignIn(snsSignInModel)
                }
                MyApplication.prefs.setLoginType("loginType", "google")
            } catch (e: ApiException) {
                // Google 로그인 실패 시
                Log.d(TAG, "Google sign in failed", e)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}