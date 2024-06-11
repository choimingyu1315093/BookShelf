package com.choisong.bookshelf.view.fragment.chat

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.R
import com.choisong.bookshelf.databinding.FragmentChatBinding
import com.choisong.bookshelf.model.chat.ChatListData
import com.choisong.bookshelf.model.chat.ChatListDataResult
import com.choisong.bookshelf.model.chat.ChatListUpdateData
import com.choisong.bookshelf.view.activity.HomeActivity
import com.choisong.bookshelf.view.adapter.ChatListBodyAdapter
import com.choisong.bookshelf.view.adapter.ChatListTopAdapter
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
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

class ChatFragment : Fragment(), ChatListBodyAdapter.OnClickListener {
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    
    companion object {
        const val TAG = "ChatFragment"
    }

    lateinit var accessToken: String

    private var mSocket: Socket? = null
    private var chatListArray = ArrayList<ChatListDataResult>()
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
        bindViews()
    }

    private fun init() = with(binding){
        accessToken = MyApplication.prefs.getAccessToken("accessToken", "")

        connectSocket()
    }

    private fun bindViews() = with(binding){
        ivBack.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_chatFragment_to_homeFragment)
        }
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

    private var onRoomInfo: Emitter.Listener = Emitter.Listener { args ->
        CoroutineScope(Dispatchers.IO).launch {
            val data = args[0] as JSONObject
            val jsonParser = JsonParser()
            val jsonObject = jsonParser.parse(data.toString()) as JsonObject
            Log.d(TAG, "onRoomInfo jsonObject $jsonObject")

            var gson = Gson()
            var chatListData = gson.fromJson(jsonObject, ChatListData::class.java)

            if(chatListData.result.isNotEmpty()){
                MyApplication.prefs.setChatUserIdx("chatUserIdx", chatListData.result[0].me_user.users.user_idx)
            }

            chatListArray.clear()
            var result = data.get("result")
            withContext(Dispatchers.Main){
                setDataToRecyclerView(result)
            }
        }
    }

    private var onChatRoomUpdate: Emitter.Listener = Emitter.Listener { args ->
        Log.d(TAG, "onChatRoomUpdate: called")
        CoroutineScope(Dispatchers.IO).launch {
            val data = args[0] as JSONObject
            val jsonParser = JsonParser()
            val jsonObject = jsonParser.parse(data.toString()) as JsonObject
            Log.d(TAG, "onChatRoomUpdate jsonObject $jsonObject")

            var gson = Gson()
            var chatListData = gson.fromJson(jsonObject, ChatListDataResult::class.java)

            chatListArray.clear()
            var result = data.get("result")
            withContext(Dispatchers.Main){
                setDataToRecyclerView(result)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setDataToRecyclerView(result: Any) = with(binding) {
        var gson = Gson()
        val listType: Type = object : TypeToken<ArrayList<ChatListDataResult>>() {}.type
        chatListArray = gson.fromJson(result.toString(), listType)

        if (chatListArray.size != 0) {
            rvChatListBody.visibility = View.VISIBLE
            ivEmpty.visibility = View.GONE
            tvEmpty.visibility = View.GONE
        } else {
            rvChatListBody.visibility = View.GONE
            ivEmpty.visibility = View.VISIBLE
            tvEmpty.visibility = View.VISIBLE
        }

        chatListBodyAdapter = ChatListBodyAdapter(chatListArray, this@ChatFragment)
        rvChatListBody.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = chatListBodyAdapter
        }
    }

    override fun goChatroom(chatroomIdx: Int, image: String, name: String) {
        val action = ChatFragmentDirections.actionChatFragmentToChatroomFragment(chatroomIdx, image, name)
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