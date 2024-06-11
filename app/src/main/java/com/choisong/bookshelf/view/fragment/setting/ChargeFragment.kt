package com.choisong.bookshelf.view.fragment.setting

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.FragmentChargeBinding
import com.choisong.bookshelf.model.TicketBuyModel
import com.choisong.bookshelf.view.activity.HomeActivity
import com.choisong.bookshelf.viewmodel.ChargeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChargeFragment : Fragment() {
    private var _binding: FragmentChargeBinding? = null
    private val binding get() = _binding!!
    private val chargeViewModel: ChargeViewModel by viewModels()

    companion object {
        const val TAG = "ChargeFragment"
    }

    lateinit var accessToken: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentChargeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        bindViews()
    }

    private fun init() = with(binding){
        accessToken = MyApplication.prefs.getAccessToken("accessToken", "")

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
//        btnPurchase.setOnClickListener {
//            chargeViewModel.buyTicket(accessToken, TicketBuyModel(1))
//        }

        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as HomeActivity).binding.bottomNavigationView.visibility = View.VISIBLE
        _binding = null
    }
}