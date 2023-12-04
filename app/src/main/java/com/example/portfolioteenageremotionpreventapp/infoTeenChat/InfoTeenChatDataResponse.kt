package com.example.portfolioteenageremotionpreventapp.infoTeenChat

data class InfoTeenChatDataResponse(
    val history: List<HistoryTeenChat>
)

data class HistoryTeenChat(
    val key: Int,
    val from: String,
    val room: String,
    val sentence: String,
    val date: String
)