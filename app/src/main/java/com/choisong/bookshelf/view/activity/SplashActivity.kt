package com.choisong.bookshelf.view.activity

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.choisong.bookshelf.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkPermission()
        bindViews()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkPermission(){
        var permission = mutableMapOf<String, String>()
        permission["fine"] = Manifest.permission.ACCESS_FINE_LOCATION
        permission["coarse"] = Manifest.permission.ACCESS_COARSE_LOCATION
        permission["write"] = Manifest.permission.WRITE_EXTERNAL_STORAGE
        permission["read"] = Manifest.permission.READ_EXTERNAL_STORAGE
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            permission["notification"] = Manifest.permission.POST_NOTIFICATIONS
        }
        permission["notification"] = Manifest.permission.POST_NOTIFICATIONS


        var denied = permission.count{
            ContextCompat.checkSelfPermission(this, it.value) == PackageManager.PERMISSION_DENIED
        }

        if(denied > 0){
            requestPermissions(permission.values.toTypedArray(), 1)
        }
    }

    private fun bindViews() = with(binding){
        btnStart.setOnClickListener {
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 1){
            grantResults.forEach {
                Log.d("TAG", "onRequestPermissionsResult: $it")
//                if(it == PackageManager.PERMISSION_DENIED){
//                    Toast.makeText(applicationContext, "서비스의 필요한 권한입니다.\n권한에 동의해주세요.", Toast.LENGTH_SHORT).show()
//                }
            }
        }
    }
}