package com.choisong.bookshelf.view.fragment.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.choisong.bookshelf.MyApplication
import com.choisong.bookshelf.databinding.FragmentChatroomBinding
import com.choisong.bookshelf.model.chat.ChatMessageData
import com.choisong.bookshelf.model.chat.ChatMessageDataResult
import com.choisong.bookshelf.view.adapter.ChatMessageAdapter
import com.choisong.bookshelf.viewmodel.ChatViewModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import dagger.hilt.android.AndroidEntryPoint
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.URISyntaxException

@AndroidEntryPoint
class ChatroomFragment : Fragment(), ChatMessageAdapter.OnClickChatListener {
    private var _binding: FragmentChatroomBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val TAG = "ChatroomFragment"
    }

    lateinit var accessToken: String

    private val chatroomIdx: ChatroomFragmentArgs by navArgs()
    private val image: ChatroomFragmentArgs by navArgs()

    private var mSocket: Socket? = null
    private var chatMessageList = arrayListOf<ChatMessageDataResult>()

    lateinit var chatMessageAdapter: ChatMessageAdapter

    private lateinit var callback: OnBackPressedCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentChatroomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        bindViews()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun init() = with(binding){
        accessToken = MyApplication.prefs.getAccessToken("accessToken", "")
        connectSocket()

        touchView.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val inputMethodManager = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(etMsg.windowToken, 0)
            }
            false
        }

        etMsg.movementMethod = ScrollingMovementMethod()
    }

    private fun bindViews() = with(binding){
        ivSend.setOnClickListener {
            var etMsg = binding.etMsg.text.toString()
            if (etMsg.isNotEmpty()) {
                var json = JSONObject()
                json.put("chat_room_idx", chatroomIdx.chatroomIdx)
                json.put("message_content", etMsg)

                mSocket?.emit("sendMessage", json)
                binding.etMsg.setText("")
            }
        }

        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun connectSocket() = with(binding){
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
            mSocket?.connect()
            mSocket?.on(Socket.EVENT_CONNECT) {
                CoroutineScope(Dispatchers.IO).launch {
                    delay(150)
                    val json = JSONObject()
                    json.put("chat_room_idx", chatroomIdx.chatroomIdx)
                    mSocket?.emit("joinRoom", json)
                }
            }
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }

        mSocket?.on("chatInfo", onChatInfo)
        mSocket?.on("receiveMessage", onNewMessage)
    }

    private var onChatInfo: Emitter.Listener = Emitter.Listener { args ->
        Log.d(TAG, "onChatInfo: called")
        requireActivity().runOnUiThread(Runnable {
            val data = args[0] as JSONObject
            val jsonParser = JsonParser()
            val jsonObject = jsonParser.parse(data.toString()) as JsonObject

            var gson = Gson()
            var chatMessageData = gson.fromJson(jsonObject, ChatMessageData::class.java)

            chatMessageAdapter = ChatMessageAdapter(chatMessageList, this, image.image)
            binding.rvChat.apply {
                layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                adapter = chatMessageAdapter
            }

            if(chatMessageData.result != null){
                for(i in 0 until chatMessageData.result!!.size){
                    chatMessageList.add(chatMessageData.result!![i])
                }
                chatMessageAdapter = ChatMessageAdapter(chatMessageList,this, image.image)
                binding.rvChat.apply {
                    adapter = chatMessageAdapter
                }
                binding.rvChat.scrollToPosition(chatMessageList.size - 1)
            }

//            checkAsRead()
        })
    }

    private var onNewMessage: Emitter.Listener = Emitter.Listener { args ->
        Log.d(TAG, "onNewMessage: called")
        requireActivity().runOnUiThread(Runnable {
            val data = args[0] as JSONObject
            val jsonParser = JsonParser()
            val jsonObject = jsonParser.parse(data.toString()) as JsonObject
            Log.d(TAG, "onNewMessage jsonObject $jsonObject")

            var gson = Gson()
            var chatMessageData = gson.fromJson(jsonObject, ChatMessageDataResult::class.java)
            chatMessageList.add(chatMessageData)
            chatMessageAdapter = ChatMessageAdapter(chatMessageList,this, image.image)
            binding.rvChat.apply {
                adapter = chatMessageAdapter
            }
            binding.rvChat.scrollToPosition(chatMessageList.size - 1)
        })
    }

    override fun onFreeInfoClicked(modelIdx: Int, isChat: Boolean) {
        Log.d(TAG, "onFreeInfoClicked: modelIdx $modelIdx, isChat $isChat")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mSocket?.off("chatInfo", onChatInfo)
        mSocket?.off("receiveMessage ", onNewMessage)

        var json = JSONObject()
        json.put("chat_room_idx", chatroomIdx.chatroomIdx)
        json.put("chat_user_idx", MyApplication.prefs.getChatUserIdx("chatUserIdx", 0))
        mSocket?.emit("exitRoom", json)

        mSocket?.disconnect()
        _binding = null
    }
}