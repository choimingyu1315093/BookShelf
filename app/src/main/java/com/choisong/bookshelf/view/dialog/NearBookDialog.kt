package com.choisong.bookshelf.view.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.databinding.FragmentNearBookDialogBinding
import com.choisong.bookshelf.model.CreateChatroomModel
import com.choisong.bookshelf.viewmodel.NearBookViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NearBookDialog(private val title: String, private val img: String, private val name: String, private val userIdx: Int, private val isbn: String, private val onDialogClose: OnDialogClose) : DialogFragment() {
    private var _binding: FragmentNearBookDialogBinding? = null
    private val binding get() = _binding!!
    private val nearBookViewModel: NearBookViewModel by viewModels()

    companion object {
        const val TAG = "NearBookDialog"
    }

    interface OnDialogClose{
        fun isClose(b: Boolean, chatroomIdx: Int)
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
        _binding = FragmentNearBookDialogBinding.inflate(inflater, container, false)
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

        txtWishBook.text = "${name}님이 읽고 싶었던"
        Glide.with(ivBook).load(img).into(ivBook)
        tvTitle.text = "<${title}>"
    }

    private fun bindViews() = with(binding){
        btnOk.setOnClickListener {
            val createChatroomModel = CreateChatroomModel(userIdx, isbn)
            nearBookViewModel.createChatroom(accessToken, createChatroomModel)
        }
    }

    private fun observeViewModel() = with(binding){
        nearBookViewModel.createChatroomMessage.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            dismiss()
        }

        nearBookViewModel.chatroomIdx.observe(viewLifecycleOwner){
            onDialogClose.isClose(true, it)
            dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}