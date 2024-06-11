package com.choisong.bookshelf.view.fragment.setting

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.databinding.FragmentChargeLogBinding
import com.choisong.bookshelf.model.TestChargeLogModel
import com.choisong.bookshelf.view.activity.HomeActivity
import com.choisong.bookshelf.view.adapter.ChargeLogAdapter
import com.choisong.bookshelf.viewmodel.ChargeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChargeLogFragment : Fragment() {
    private var _binding: FragmentChargeLogBinding? = null
    private val binding get() = _binding!!
    private val chargeViewModel: ChargeViewModel by viewModels()

    companion object {
        const val TAG = "ChargeLogFragment"
    }

    private lateinit var accessToken: String

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
        observeViewModel()
    }

    private fun init() = with(binding){
        accessToken = MyApplication.prefs.getAccessToken("accessToken", "")
        chargeViewModel.getTicketLog(accessToken)
    }

    private fun bindViews() = with(binding){
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeViewModel() = with(binding){
        chargeViewModel.tickerLogoList.observe(viewLifecycleOwner){
            if(it.size != 0){
                chargeLogAdapter = ChargeLogAdapter(requireContext(), it)
                rvCharge.apply {
                    layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                    adapter = chargeLogAdapter
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as HomeActivity).binding.bottomNavigationView.visibility = View.VISIBLE
        _binding = null
    }
}