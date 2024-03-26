package com.choisong.bookshelf.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
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

        appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.bookFragment, R.id.chatFragment, R.id.profileFragment))

        bottomNavigationView.setupWithNavController(navController)
    }

//    override fun onBackPressed() {
//        if(backKeyPressedTime + 3000 > System.currentTimeMillis()){
//            super.onBackPressed()
//            finish()
//        }else {
//            Toast.makeText(this, "한번 더 뒤로가기 버튼을 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
//        }
//
//        backKeyPressedTime = System.currentTimeMillis()
//    }

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
                    }else {
                        if(backKeyPressedTime + 3000 > System.currentTimeMillis()){
                            super.onBackPressed()
                            finish()
                        }else {
                            Toast.makeText(this, "한번 더 뒤로가기 버튼을 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
                        }

                        backKeyPressedTime = System.currentTimeMillis()
                    }
//                    if(extractedText == "AddProjectManagerFragment" || extractedText == "BrandManagerFragment" || extractedText == "MarketingManagerFragment"
//                        || extractedText == "EmployeeManagementFragment" || extractedText == "MessageListFragment" || extractedText == "BillFragment"
//                        || extractedText == "CardFragment" || extractedText == "BankFragment" || extractedText == "MyPageFragment"){
//                        if(backKeyPressedTime + 3000 > System.currentTimeMillis()){
//                            super.onBackPressed()
//                            finish()
//                        }else {
//                            Toast.makeText(this, "한번 더 뒤로가기 버튼을 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
//                        }
//
//                        backKeyPressedTime = System.currentTimeMillis()
//                    }else if(extractedText == "AddEmployeeFragment"){
//                        Navigation.findNavController(binding.fragmentContainerView).navigate(R.id.action_addEmployeeFragment_to_employeeManagementFragment)
//                    }else if(extractedText == "AddProjectManagerDetailFragment"){
//                        Navigation.findNavController(binding.fragmentContainerView).navigate(R.id.action_addProjectManagerDetailFragment_to_addProjectManagerFragment)
//                    }else if(extractedText == "EmployeeDetailFragment"){
//                        Navigation.findNavController(binding.fragmentContainerView).navigate(R.id.action_employeeDetailFragment_to_employeeManagementFragment)
//                    }else if(extractedText == "MarketingDetailFragment"){
//                        Navigation.findNavController(binding.fragmentContainerView).navigate(R.id.action_marketingDetailFragment_to_marketingManagerFragment)
//                    }else {
//                        super.onBackPressed()
//                    }
                }else {
                    Log.d("TAG", "onBackPressed: 매칭되는 부분이 없습니다.")
                }
            }
        } else {
            Log.d("TAG", "onBackPressed: 호출")
        }
    }
}