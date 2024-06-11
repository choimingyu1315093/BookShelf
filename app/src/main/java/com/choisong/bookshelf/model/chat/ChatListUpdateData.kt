package com.choisong.bookshelf.model.chat

data class ChatListUpdateData(
    val result: ChatListUpdateDataResult
)

data class ChatListUpdateDataResult(
    val books: UpdateBooks,
    val chat_room_idx: Int,
    val me_user: UpdateMeUser,
    val opponent_user: List<UpdateOpponentUser>,
    val recent_message: String,
    val recent_message_date: String
)

data class UpdateBooks(
    val book_image: String
)

data class UpdateMeUser(
    val unread_count: Int,
    val users: UpdateUsers
)

data class UpdateOpponentUser(
    val unread_count: Int,
    val users: UpdateUsers
)


data class UpdateUsers(
    val user_idx: Int,
    val user_name: String
)