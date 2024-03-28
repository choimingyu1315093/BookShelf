package com.choisong.bookshelf.view.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.databinding.FragmentUserDeleteDialogBinding
import com.choisong.bookshelf.viewmodel.UserDeleteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDeleteDialog : DialogFragment() {
    private var _binding: FragmentUserDeleteDialogBinding? = null
    private val binding get() = _binding!!
    private val userDeleteViewModel: UserDeleteViewModel by viewModels()

    companion object {
        const val TAG = "UserDeleteDialog"
    }

    lateinit var accessToken: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        val width = resources.displayMetrics.widthPixels
        dialog?.window?.setLayout((width * 0.9).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentUserDeleteDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        bindViews()
        observeViewModel()
    }

    private fun init() = with(binding){
        accessToken = MyApplication.prefs.getAccessToken("accessToken", "")
    }

    private fun bindViews() = with(binding){
        btnCancel.setOnClickListener { dismiss() }
        btnOk.setOnClickListener {
            userDeleteViewModel.userDelete(accessToken)
        }
    }

    private fun observeViewModel() = with(binding){
        userDeleteViewModel.userDeleteResult.observe(viewLifecycleOwner){
            if(it){
                Toast.makeText(requireContext(), "탈퇴하셨습니다\n그동안 이용해 주셔서 대단히 감사합니다.", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}