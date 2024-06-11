package com.choisong.bookshelf.view.activity

import android.Manifest
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.databinding.ActivitySplashBinding
import com.choisong.bookshelf.view.fragment.login.LoginFragment
import com.google.firebase.messaging.FirebaseMessaging
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    companion object {
        const val TAG = "SplashActivity"
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestPermission()
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