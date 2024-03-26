package com.choisong.bookshelf.model.chat

data class ChatMessageData(
    val result: ArrayList<ChatMessageDataResult>?
)

data class ChatMessageDataResult(
    val chat_message_idx: Int,
    val create_date: String,
    val message_content: String,
    val users: chatMessageUsers
)

data class chatMessageUsers(
    val user_idx: Int,
    val user_name: String
)