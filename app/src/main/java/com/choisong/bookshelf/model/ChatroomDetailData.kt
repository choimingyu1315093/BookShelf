package com.choisong.bookshelf.model

data class ChatroomDetailData(
    val result: Boolean,
    val data: ChatroomDetailDataResult
)

data class ChatroomDetailDataResult(
    val isExitMe: Int,
    val result: ChatroomDetailDataResultDetail
)

data class ChatroomDetailDataResultDetail(
    val chat_room_idx: Int,
    val chat_users: List<ChatroomDetailUser>
)

data class ChatroomDetailUser(
    val chat_user_idx: Int,
    val is_exit: Int,
    val users: ChatroomDetailUserIdx
)

data class ChatroomDetailUserIdx(
    val user_idx: Int
)