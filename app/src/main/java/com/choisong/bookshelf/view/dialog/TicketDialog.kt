package com.choisong.bookshelf.view.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.FragmentTicketDialogBinding
import com.choisong.bookshelf.model.CreateChatroomModel
import com.choisong.bookshelf.viewmodel.NearBookViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TicketDialog(private val userIdx: Int) : DialogFragment() {
    private var _binding: FragmentTicketDialogBinding? = null
    private val binding get() = _binding!!
    private val nearBookViewModel: NearBookViewModel by viewModels()

    companion object{
        const val TAG = "TicketDialog"
    }

    lateinit var accessToken: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTicketDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        bindViews()
    }

    private fun init() = with(binding){
        accessToken = MyApplication.prefs.getAccessToken("accessToken", "")
    }

    private fun bindViews() = with(binding){
        btnCancel.setOnClickListener { dismiss() }

        btnOk.setOnClickListener {
//            val createChatroomModel = CreateChatroomModel(userIdx)
//            nearBookViewModel.createChatroom(accessToken, createChatroomModel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}