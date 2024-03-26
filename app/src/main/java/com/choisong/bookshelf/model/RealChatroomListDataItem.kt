package com.choisong.bookshelf.model

data class RealChatroomListDataItem(
    val books: Books,
    val chat_room_idx: Int,
    val chat_users: List<ChatroomListUser>,
    val recent_message: String,
    val recent_message_date: String?
)