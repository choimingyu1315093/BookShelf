package com.choisong.bookshelf.model

data class RealChatroomListDataItemX(
    val books: BooksX,
    val chat_room_idx: Int,
    val chat_users: List<ChatroomListUser>,
    val recent_message: String,
    val recent_message_date: String?
)