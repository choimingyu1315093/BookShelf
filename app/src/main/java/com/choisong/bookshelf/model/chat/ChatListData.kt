package com.choisong.bookshelf.model.chat

data class ChatListData(
    val result: List<ChatListDataResult>
)

data class ChatListDataResult(
    val books: Books,
    val chat_room_idx: Int,
    val me_user: MeUser,
    val opponent_user: List<OpponentUser>,
    val recent_message: String,
    val recent_message_date: String
)

data class Books(
    val book_image: String
)

data class MeUser(
    val unread_count: Int,
    val users: Users
)

data class OpponentUser(
    val unread_count: Int,
    val users: Users
)


data class Users(
    val user_idx: Int,
    val user_name: String
)