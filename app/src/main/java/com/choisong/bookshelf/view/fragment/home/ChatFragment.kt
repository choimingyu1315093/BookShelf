package com.choisong.bookshelf.view.fragment.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.databinding.FragmentChatBinding
import com.choisong.bookshelf.model.RealChatroomListDataItem
import com.choisong.bookshelf.model.chat.ChatListData
import com.choisong.bookshelf.model.chat.ChatListDataResult
import com.choisong.bookshelf.view.activity.HomeActivity
import com.choisong.bookshelf.view.adapter.ChatListBodyAdapter
import com.choisong.bookshelf.view.adapter.ChatListTopAdapter
import com.choisong.bookshelf.viewmodel.ChatViewModel
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import dagger.hilt.android.AndroidEntryPoint
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.lang.reflect.Type
import java.net.URISyntaxException

@AndroidEntryPoint
class ChatFragment : Fragment(), ChatListBodyAdapter.OnClickListener {
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private val chatViewModel: ChatViewModel by viewModels()
    
    companion object {
        const val TAG = "ChatFragment"
    }

    lateinit var accessToken: String

    private var mSocket: Socket? = null
    private var chatListArray = ArrayList<ChatListDataResult>()
    lateinit var chatListTopAdapter: ChatListTopAdapter
    lateinit var chatListBodyAdapter: ChatListBodyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() = with(binding){
        (requireActivity() as HomeActivity).binding.bottomNavigationView.visibility = View.VISIBLE
        accessToken = MyApplication.prefs.getAccessToken("accessToken", "")

        connectSocket()
    }

    private fun connectSocket(){
        try {
            val opts = IO.Options()

            val headers = HashMap<String, List<String>>()
            headers["token"] = listOf(accessToken)
            opts.extraHeaders = headers
            opts.forceNew = true
            opts.reconnection = false
            mSocket =
                IO.socket(
                    "https://bookshelf-chat-dev.jongwoong3341.com",
                    opts
                )
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }

        mSocket?.on("roomInfo", onRoomInfo)
        mSocket?.on("chatRoomUpdate", onChatRoomUpdate)
        mSocket?.connect()
    }

    private var onChatRoomUpdate: Emitter.Listener = Emitter.Listener { args ->
        Log.d(TAG, "onChatRoomUpdate: called")
        CoroutineScope(Dispatchers.IO).launch {
            val data = args[0] as JSONObject
            val jsonParser = JsonParser()
            val jsonObject = jsonParser.parse(data.toString()) as JsonObject

            var gson = Gson()
            var chatListData = gson.fromJson(jsonObject, ChatListDataResult::class.java)

            chatListArray.clear()
            setDataToRecyclerView(chatListData, 2)
        }
    }

    private var onRoomInfo: Emitter.Listener = Emitter.Listener { args ->
        CoroutineScope(Dispatchers.IO).launch {
            val data = args[0] as JSONObject
            val jsonParser = JsonParser()
            val jsonObject = jsonParser.parse(data.toString()) as JsonObject

            var gson = Gson()
            var chatListData = gson.fromJson(jsonObject, ChatListData::class.java)

            if(chatListData.result.size != 0){
                MyApplication.prefs.setChatUserIdx("chatUserIdx", chatListData.result[0].me_user.users.user_idx)
            }

            chatListArray.clear()
            var result = data.get("result")
            withContext(Dispatchers.Main){
                setDataToRecyclerView(result, 1)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setDataToRecyclerView(result: Any, type: Int) = with(binding) {
        var gson = Gson()
        val listType: Type = object : TypeToken<ArrayList<ChatListDataResult>>() {}.type
        chatListArray = gson.fromJson(result.toString(), listType)

        if (chatListArray.size != 0) {
            rvChatListTop.visibility = View.VISIBLE
            rvChatListBody.visibility = View.VISIBLE
            ivEmpty.visibility = View.GONE
            tvEmpty.visibility = View.GONE
        } else {
            rvChatListTop.visibility = View.GONE
            rvChatListBody.visibility = View.GONE
            ivEmpty.visibility = View.VISIBLE
            tvEmpty.visibility = View.VISIBLE
        }

        chatListTopAdapter = ChatListTopAdapter(chatListArray)
        rvChatListTop.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = chatListTopAdapter
        }

        chatListBodyAdapter = ChatListBodyAdapter(chatListArray, this@ChatFragment)
        rvChatListBody.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = chatListBodyAdapter
        }
    }

    override fun goChatroom(chatroomIdx: Int, image: String) {
        val action = ChatFragmentDirections.actionChatFragmentToChatroomFragment(chatroomIdx, image)
        Navigation.findNavController(binding.root).navigate(action)
        (requireActivity() as HomeActivity).binding.bottomNavigationView.visibility = View.GONE
    }


    override fun onDestroyView() {
        super.onDestroyView()
        mSocket?.off("roomInfo", onRoomInfo)
        mSocket?.off("chatRoomUpdate ", onChatRoomUpdate)
        mSocket?.disconnect()
        _binding = null
    }
}