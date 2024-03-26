package com.choisong.bookshelf.model

data class ChatroomListData(
    val result: Boolean,
    val data: ArrayList<ChatroomListDataResult>
)

data class ChatroomListDataResult(
    val chat_room_idx: Int,
    val chat_users: List<ChatroomListUser>
)

data class ChatUser(
    val chat_user_idx: Int,
    val users: CUsers
)

data class CUsers(
    val user_idx: Int,
    val user_name: String
)