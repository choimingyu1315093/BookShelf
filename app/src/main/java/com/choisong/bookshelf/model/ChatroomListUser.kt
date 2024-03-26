package com.choisong.bookshelf.model

data class ChatroomListUser(
    val chat_user_idx: Int,
    val unread_count: Int,
    val users: ChatroomListUsers
)