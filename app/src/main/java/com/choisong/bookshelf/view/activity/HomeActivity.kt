package com.choisong.bookshelf.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding

    lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration

    var backKeyPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() = with(binding){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.bookFragment, R.id.mapFragment, R.id.profileFragment, R.id.settingFragment))

        bottomNavigationView.setupWithNavController(navController)
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
                    Log.d("TAG", "onBackPressed: $extractedText")
                    if(extractedText == "ChatroomFragment"){
                        Navigation.findNavController(binding.fragmentContainerView).navigate(R.id.action_chatroomFragment_to_chatFragment)
                    }else if(extractedText == "ChatFragment"){
                        Navigation.findNavController(binding.fragmentContainerView).navigate(R.id.action_chatFragment_to_homeFragment)
                    }else if(extractedText == "ProfileChangeFragment"){
                        Navigation.findNavController(binding.fragmentContainerView).popBackStack()
                    }else if(extractedText == "ChargeLogFragment"){
                        Navigation.findNavController(binding.fragmentContainerView).popBackStack()
                    }else if(extractedText == "PasswordChangeFragment"){
                        Navigation.findNavController(binding.fragmentContainerView).popBackStack()
                    }else if(extractedText == "SecessionFragment"){
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