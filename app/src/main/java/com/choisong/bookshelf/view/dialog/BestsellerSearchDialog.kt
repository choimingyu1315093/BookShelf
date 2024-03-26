package com.choisong.bookshelf.view.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.databinding.FragmentBestsellerSearchDialogBinding
import com.choisong.bookshelf.view.adapter.BestsellerSearchForeignAdapter
import com.choisong.bookshelf.view.adapter.BestsellerSearchKoreaAdapter


class BestsellerSearchDialog(private val onDialogCancel: OnDialogCancel) : DialogFragment(), BestsellerSearchForeignAdapter.OnClickListener, BestsellerSearchKoreaAdapter.OnClickListener {
    private var _binding: FragmentBestsellerSearchDialogBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val TAG = "BestsellerSearchDialog"
    }

    interface OnDialogCancel{
        fun next(b: Boolean)
    }

    lateinit var bestsellerSearchKoreaAdapter: BestsellerSearchKoreaAdapter
    lateinit var bestsellerSearchForeignAdapter: BestsellerSearchForeignAdapter

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
        _binding = FragmentBestsellerSearchDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        bindViews()
    }

    private fun init() = with(binding){
        bestsellerSearchKoreaAdapter = BestsellerSearchKoreaAdapter(requireContext(), this@BestsellerSearchDialog)
        rvKoreaFilter.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = bestsellerSearchKoreaAdapter
        }

        bestsellerSearchForeignAdapter = BestsellerSearchForeignAdapter(requireContext(), this@BestsellerSearchDialog)
        rvForeignFilter.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = bestsellerSearchForeignAdapter
        }
    }

    private fun bindViews() = with(binding){
        btnCancel.setOnClickListener { dismiss() }
        btnOk.setOnClickListener {
            onDialogCancel.next(true)
            dismiss()
        }
    }

    //베스트셀러 외국도서 조건 선택
    override fun foreignItemClick(b: Boolean, c: String, n: Int) {
        MyApplication.prefs.setBestsellerCountry("country", c)
        MyApplication.prefs.setBestsellerNum("num", n)
        bestsellerSearchKoreaAdapter = BestsellerSearchKoreaAdapter(requireContext(), this)
        binding.rvKoreaFilter.apply {
            adapter = bestsellerSearchKoreaAdapter
        }
    }

    override fun koreaItemClick(b: Boolean, c: String, n: Int) {
        MyApplication.prefs.setBestsellerCountry("country", c)
        MyApplication.prefs.setBestsellerNum("num", n)
        bestsellerSearchForeignAdapter = BestsellerSearchForeignAdapter(requireContext(), this)
        binding.rvForeignFilter.apply {
            adapter = bestsellerSearchForeignAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}