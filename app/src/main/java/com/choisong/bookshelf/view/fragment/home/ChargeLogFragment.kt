package com.choisong.bookshelf.view.fragment.home

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.FragmentChargeLogBinding
import com.choisong.bookshelf.model.TestChargeLogModel
import com.choisong.bookshelf.view.adapter.ChargeLogAdapter


class ChargeLogFragment : Fragment() {
    private var _binding: FragmentChargeLogBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val TAG = "ChargeLogFragment"
    }

    private var chargeLogList = arrayListOf<TestChargeLogModel>()
    lateinit var chargeLogAdapter: ChargeLogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentChargeLogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        bindViews()
    }

    private fun init() = with(binding){
        var testChargeLog = TestChargeLogModel(100, "오후 4:45", "충전")
        var testChargeLog2 = TestChargeLogModel(-100, "오후 7:17", "채팅하기")
        var testChargeLog3 = TestChargeLogModel(100, "오후 4:45", "충전")
        var testChargeLog4 = TestChargeLogModel(100, "오후 4:45", "충전")
        var testChargeLog5 = TestChargeLogModel(100, "오후 4:45", "충전")
        var testChargeLog6 = TestChargeLogModel(100, "오후 4:45", "충전")
        var testChargeLog7 = TestChargeLogModel(100, "오후 4:45", "충전")
        var testChargeLog8 = TestChargeLogModel(100, "오후 4:45", "충전")
        var testChargeLog9 = TestChargeLogModel(100, "오후 4:45", "충전")
        var testChargeLog10 = TestChargeLogModel(100, "오후 4:45", "충전")

        chargeLogList.add(testChargeLog)
        chargeLogList.add(testChargeLog2)
        chargeLogList.add(testChargeLog3)
        chargeLogList.add(testChargeLog4)
        chargeLogList.add(testChargeLog5)
        chargeLogList.add(testChargeLog6)
        chargeLogList.add(testChargeLog7)
        chargeLogList.add(testChargeLog8)
        chargeLogList.add(testChargeLog9)
        chargeLogList.add(testChargeLog10)
        chargeLogList.add(testChargeLog10)
        chargeLogList.add(testChargeLog10)
        chargeLogList.add(testChargeLog10)
        chargeLogList.add(testChargeLog10)

        chargeLogAdapter = ChargeLogAdapter(requireContext(), chargeLogList)
        rvCharge.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = chargeLogAdapter
        }

        view?.isFocusableInTouchMode = true
        view?.requestFocus()
        view?.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                findNavController().popBackStack()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }

    private fun bindViews() = with(binding){
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}