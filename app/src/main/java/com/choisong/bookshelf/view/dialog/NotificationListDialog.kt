package com.choisong.bookshelf.view.dialog

import android.app.Dialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.FragmentNotificationListDialogBinding
import com.choisong.bookshelf.view.adapter.NotificationAdapter
import com.choisong.bookshelf.viewmodel.NotificationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationListDialog : DialogFragment(), NotificationAdapter.OnClickListener, NotificationAllDeleteDialog.DeleteAll {
    private var _binding: FragmentNotificationListDialogBinding? = null
    private val binding get() = _binding!!
    private val notificationViewModel: NotificationViewModel by viewModels()

    companion object {
        const val TAG = "NotificationDialog"
    }

    lateinit var accessToken: String
    lateinit var notificationAdapter: NotificationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentNotificationListDialogBinding.inflate(inflater, container, false)
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val window = dialog?.window
            window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window?.statusBarColor = Color.WHITE
            window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    private fun bindViews() = with(binding){
        txtClear.setOnClickListener {
            val dialog = NotificationAllDeleteDialog(this@NotificationListDialog)
            dialog.show(requireActivity().supportFragmentManager, "NotificationAllDeleteDialog")
        }

        ivBack.setOnClickListener {
            dismiss()
        }
    }

    private fun observeViewModel() = with(binding){
        notificationViewModel.alarmListResult.observe(viewLifecycleOwner){
            if(it.data?.size != 0){
                notificationAdapter = NotificationAdapter(it.data!!, this@NotificationListDialog)
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