package com.choisong.bookshelf.view.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.databinding.FragmentReviewDialogBinding
import com.choisong.bookshelf.model.AddCommentModel
import com.choisong.bookshelf.model.UpdateCommentModel
import com.choisong.bookshelf.viewmodel.ReviewViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewDialog(private val bookIsbn: String, private val onDialogCloseListener: OnDialogCloseListener) : DialogFragment() {
    private var _binding: FragmentReviewDialogBinding? = null
    private val binding get() = _binding!!
    private val reviewViewModel: ReviewViewModel by viewModels()

    companion object {
        const val TAG = "ReviewDialog"
    }

    interface OnDialogCloseListener {
        fun commentReload(b: Boolean)
    }

    private lateinit var accessToken: String
    private var comment = ""

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
        _binding = FragmentReviewDialogBinding.inflate(inflater, container, false)
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
            inputManager.hideSoftInputFromWindow(etReview.windowToken, 0)
        }

        etReview.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                comment = s.toString().trim()
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })
    }

    private fun bindViews() = with(binding){
        btnCancel.setOnClickListener { dismiss() }

        btnOk.setOnClickListener {
            if(comment == ""){
                Toast.makeText(requireContext(), "내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }else {
                val addCommentModel = AddCommentModel(bookIsbn, comment, rb.rating.toDouble())
                reviewViewModel.addComment(accessToken, addCommentModel)
            }
        }
    }

    private fun observeViewModel() = with(binding){
        reviewViewModel.addCommentResult.observe(viewLifecycleOwner){
            if(it){
                dismiss()
                onDialogCloseListener.commentReload(true)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}