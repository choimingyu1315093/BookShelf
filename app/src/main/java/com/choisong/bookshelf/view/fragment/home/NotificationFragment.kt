package com.choisong.bookshelf.view.fragment.home

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.FragmentNotificationBinding
import com.choisong.bookshelf.view.adapter.NotificationAdapter
import com.choisong.bookshelf.view.dialog.NotificationAllDeleteDialog
import com.choisong.bookshelf.viewmodel.NotificationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment : Fragment(), NotificationAdapter.OnClickListener, NotificationAllDeleteDialog.DeleteAll {
    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!
    private val notificationViewModel: NotificationViewModel by viewModels()

    companion object {
        const val TAG = "NotificationFragment"
    }

    lateinit var accessToken: String
    lateinit var notificationAdapter: NotificationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
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
        notificationViewModel.alarmList(accessToken)

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
        txtClear.setOnClickListener {
            val dialog = NotificationAllDeleteDialog(this@NotificationFragment)
            dialog.show(requireActivity().supportFragmentManager, "NotificationAllDeleteDialog")
        }

        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeViewModel() = with(binding){
        notificationViewModel.alarmListResult.observe(viewLifecycleOwner){
            if(it.data?.size != 0){
                notificationAdapter = NotificationAdapter(it.data!!, this@NotificationFragment)
                rvNotification.apply {
                    layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                    adapter = notificationAdapter
                }
                rvNotification.visibility = View.VISIBLE
                txtClear.visibility = View.VISIBLE
                txtEmptyNotification.visibility = View.GONE
                ivEmptyNotification.visibility = View.GONE
            }else {
                rvNotification.visibility = View.GONE
                txtClear.visibility = View.GONE
                txtEmptyNotification.visibility = View.VISIBLE
                ivEmptyNotification.visibility = View.VISIBLE
            }
        }

        notificationViewModel.deleteAlarmResult.observe(viewLifecycleOwner){
            if(it){
                notificationViewModel.alarmList(accessToken)
            }
        }

        notificationViewModel.deleteAllAlarmResult.observe(viewLifecycleOwner){
            if(it){
                notificationViewModel.alarmList(accessToken)
            }
        }
    }

    //알람 하나 삭제
    override fun deleteItem(alarmIdx: Int) {
        notificationViewModel.deleteAlarm(accessToken, alarmIdx)
    }

    //전체삭제
    override fun deleteAll(b: Boolean) {
        Toast.makeText(requireContext(), "모든 알림을 삭제했습니다.", Toast.LENGTH_SHORT).show()
        notificationViewModel.deleteAllAlarm(accessToken)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}