package com.choisong.bookshelf.view.fragment.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.FragmentProfileChangeBinding
import com.choisong.bookshelf.view.activity.HomeActivity
import com.choisong.bookshelf.view.dialog.DescriptionDialog
import com.choisong.bookshelf.view.dialog.NicknameDialog
import com.choisong.bookshelf.view.dialog.ReviewDialog
import com.choisong.bookshelf.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileChangeFragment : Fragment(), NicknameDialog.OnNicknameListener, DescriptionDialog.OnDescriptionListener {
    private var _binding: FragmentProfileChangeBinding? = null
    private val binding get() = _binding!!
    private val profileViewModel: ProfileViewModel by viewModels()

    companion object {
        const val TAG = "ProfileChangeFragment"
    }

    lateinit var accessToken: String
    private var nick = ""
    private var des = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProfileChangeBinding.inflate(inflater, container, false)
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

        tvNickname.text = MyApplication.prefs.getNickname("nickname", "")
        tvDescription.text = MyApplication.prefs.getDescription("description", "")
    }

    private fun bindViews() = with(binding){
        ivNickname.setOnClickListener {
            val dialog = NicknameDialog(this@ProfileChangeFragment)
            dialog.show(requireActivity().supportFragmentManager, "NicknameDialog")
        }

        ivDescription.setOnClickListener {
            val dialog = DescriptionDialog(this@ProfileChangeFragment)
            dialog.show(requireActivity().supportFragmentManager, "DescriptionDialog")
        }

        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeViewModel() = with(binding){
        profileViewModel.nicknameResult.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), "닉네임을 변경하셨습니다.", Toast.LENGTH_SHORT).show()
            tvNickname.text = nick
        }

        profileViewModel.descriptionResult.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), "한 줄 메시지를 변경하셨습니다.", Toast.LENGTH_SHORT).show()
            tvDescription.text = des
        }
    }

    override fun saveNickname(nickname: String) {
        profileViewModel.saveNickname(accessToken, nickname)
        nick = nickname
    }

    override fun saveDescription(description: String) {
        profileViewModel.saveDescription(accessToken, description)
        des = description
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as HomeActivity).binding.bottomNavigationView.visibility = View.VISIBLE
        _binding = null
    }
}