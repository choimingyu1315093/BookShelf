package com.choisong.bookshelf.view.fragment.signup

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.choisong.bookshelf.databinding.FragmentSignUpSuccessBinding
import com.choisong.bookshelf.view.activity.LoginActivity
import com.choisong.bookshelf.viewmodel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignUpSuccessFragment : DialogFragment() {
    private var _binding: FragmentSignUpSuccessBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val TAG = "SignUpSuccessFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        val width = resources.displayMetrics.widthPixels
        dialog?.window?.setLayout((width * 0.9).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSignUpSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.Main.immediate).launch {
            finish()
        }
    }

    private suspend fun finish(){
        Toast.makeText(requireContext(), "가입해주셔서 감사합니다.\n이벤트로 교환권을 지급해 드립니다.", Toast.LENGTH_SHORT).show()
        delay(4300)
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}