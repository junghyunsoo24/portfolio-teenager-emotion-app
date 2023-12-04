package com.example.portfolioteenageremotionpreventapp.infoChatbot

data class InfoChatBotDataResponse(
    val history: List<HistoryChatBot>
)

data class HistoryChatBot(
    val key: Int,
    val teen_id: String,
    val teen_message: String,
    val teen_chat_date: String,
    val chatbot_message: String,
    val chatbot_chat_date: String
)