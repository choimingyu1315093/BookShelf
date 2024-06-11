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
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.FragmentDescriptionDialogBinding

class DescriptionDialog(private val onDescriptionListener: OnDescriptionListener) : DialogFragment() {
    private var _binding: FragmentDescriptionDialogBinding? = null
    private val binding get() = _binding!!

    interface OnDescriptionListener{
        fun saveDescription(description: String)
    }

    companion object {
        const val TAG = "DescriptionDialog"
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
        _binding = FragmentDescriptionDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        bindViews()
    }

    private fun init() = with(binding){
        etDescription.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString() == ""){
                    tvCount.text = "0/200"
                }else {
                    tvCount.text = "${s.toString().length}/200"
                }
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })
    }

    private fun bindViews() = with(binding){
        btnCancel.setOnClickListener { dismiss() }
        btnOk.setOnClickListener {
            dismiss()
            onDescriptionListener.saveDescription(etDescription.text.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}