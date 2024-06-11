package com.choisong.bookshelf.view.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.FragmentDeleteChatroomDialogBinding
import com.choisong.bookshelf.viewmodel.ChatViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteChatroomDialog(private val chatroomIdx: Int, private val onChatroomOutListener: OnChatroomOutListener) : DialogFragment() {
    private var _binding: FragmentDeleteChatroomDialogBinding? = null
    private val binding get() = _binding!!
    private val chatViewModel: ChatViewModel by viewModels()

    interface OnChatroomOutListener {
        fun outChatroom(b: Boolean)
    }

    companion object {
        const val TAG = "DeleteChatroomDialog"
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
        _binding = FragmentDeleteChatroomDialogBinding.inflate(inflater, container, false)
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
            chatViewModel.deleteChatroom(accessToken, chatroomIdx)
        }
    }

    private fun observeViewModel() = with(binding){
        chatViewModel.deleteResult.observe(viewLifecycleOwner){
            if(it){
                Toast.makeText(requireContext(), "채팅을 종료하였습니다.", Toast.LENGTH_SHORT).show()
                dismiss()
                onChatroomOutListener.outChatroom(true)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}