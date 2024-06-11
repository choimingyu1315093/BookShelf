package com.choisong.bookshelf.view.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.FragmentMemoDialogBinding
import com.choisong.bookshelf.model.AddCommentModel
import com.choisong.bookshelf.model.AddMemoModel
import com.choisong.bookshelf.viewmodel.MemoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MemoDialog(private val bookIsbn: String, private val onDialogCloseListener: OnDialogCloseListener) : DialogFragment() {
    private var _binding: FragmentMemoDialogBinding? = null
    private val binding get() = _binding!!
    private val memoViewModel: MemoViewModel by viewModels()

    companion object {
        const val TAG = "MemoDialog"
    }

    interface OnDialogCloseListener {
        fun memoReload(b: Boolean)
    }

    private lateinit var accessToken: String
    private var memo = ""
    private var isPublic = "y"

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
        _binding = FragmentMemoDialogBinding.inflate(inflater, container, false)
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
        cl.setOnClickListener {
            val inputManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(etMemo.windowToken, 0)
        }

        etMemo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                memo = s.toString().trim()
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })
        
        rg.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.rb1 -> {
                    isPublic = "y"
                }
                R.id.rb2 -> {
                    isPublic = "n"
                }
            }
        }
    }

    private fun bindViews() = with(binding){
        btnCancel.setOnClickListener { dismiss() }

        btnOk.setOnClickListener {
            if(memo == ""){
                Toast.makeText(requireContext(), "내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }else {
                val addMemoModel = AddMemoModel(memo, isPublic, bookIsbn)
                memoViewModel.addMemo(accessToken, addMemoModel)
            }
        }
    }

    private fun observeViewModel() = with(binding){
        memoViewModel.addMemoResult.observe(viewLifecycleOwner){
            if(it){
                dismiss()
                onDialogCloseListener.memoReload(true)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}