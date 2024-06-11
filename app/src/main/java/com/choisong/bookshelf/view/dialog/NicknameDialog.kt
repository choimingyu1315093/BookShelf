package com.choisong.bookshelf.view.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.FragmentNicknameDialogBinding
import com.choisong.bookshelf.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NicknameDialog(private val onNicknameListener: OnNicknameListener) : DialogFragment() {
    private var _binding: FragmentNicknameDialogBinding? = null
    private val binding get() = _binding!!
    private val profileViewModel: ProfileViewModel by viewModels()

    interface OnNicknameListener{
        fun saveNickname(nickname: String)
    }

    companion object {
        const val TAG = "NicknameDialog"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        val width = resources.displayMetrics.widthPixels
        dialog?.window?.setLayout((width * 0.9).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentNicknameDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        bindViews()
        observeViewModel()
    }

    private fun init() = with(binding){
        etNickname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString() == ""){
                    tvCount.visibility = View.GONE
                    ivDelete.visibility = View.GONE
                }else {
                    profileViewModel.nicknameCheck(s.toString())
                    tvCount.visibility = View.VISIBLE
                    tvCount.text = "${s.toString().length}/20"
                    ivDelete.visibility = View.VISIBLE
                }
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })
    }

    private fun bindViews() = with(binding){
        ivDelete.setOnClickListener {
            etNickname.setText("")
        }

        btnCancel.setOnClickListener { dismiss() }
        btnOk.setOnClickListener {
            dismiss()
            onNicknameListener.saveNickname(etNickname.text.toString())
        }
    }

    private fun observeViewModel() = with(binding){
        profileViewModel.nicknameCheckResult.observe(viewLifecycleOwner){
            if(it){
                txtNicknameWarning.visibility = View.GONE
                btnOk.isEnabled = true
            }else {
                txtNicknameWarning.visibility = View.VISIBLE
                btnOk.isEnabled = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}