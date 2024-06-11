package com.choisong.bookshelf.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    companion object {
        const val TAG = "LoginActivity"
    }

    var backKeyPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onBackPressed() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navBackStackEntry = navHostFragment.navController.currentBackStackEntry
        val currentFragmentId = navBackStackEntry?.destination?.id
        if (currentFragmentId != null) {
            val navGraph = navHostFragment.navController.graph
            val destination = navGraph.findNode(currentFragmentId)
            if (destination is FragmentNavigator.Destination) {
                val fragmentClass = destination.className
                val pattern = Pattern.compile("\\.([^\\.]+)\\z")
                val matcher = pattern.matcher(fragmentClass)
                if (matcher.find()) {
                    val extractedText = matcher.group(1)
                    if(extractedText == "FindPwFragment" || extractedText == "FindIdFragment"){
                        Navigation.findNavController(binding.fragmentContainerView).popBackStack()
                    }else {
                        if(backKeyPressedTime + 3000 > System.currentTimeMillis()){
                            super.onBackPressed()
                            finish()
                        }else {
                            Toast.makeText(this, "한번 더 뒤로가기 버튼을 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
                        }

                        backKeyPressedTime = System.currentTimeMillis()
                    }
                }else {
                    Log.d("TAG", "onBackPressed: 매칭되는 부분이 없습니다.")
                }
            }
        } else {
            Log.d("TAG", "onBackPressed: 호출")
        }
    }
}