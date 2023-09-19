package com.example.portfolioteenageremotionpreventapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.portfolioteenageremotionpreventapp.R
import com.example.portfolioteenageremotionpreventapp.chatbot.ChatBotDataPair

class ChatBotAdapter(private val chatBotData: List<ChatBotDataPair>) :
    RecyclerView.Adapter<ChatBotAdapter.MessageViewHolder>() {

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageItemTextView: TextView = itemView.findViewById(R.id.chatBotTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chatbot, parent, false)
        return MessageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val messagePair = chatBotData[position]
        val messageInfo = "입력: ${messagePair.inputMessage}\n응답: ${messagePair.responseMessage}\n"
        holder.messageItemTextView.text = messageInfo

    }

    override fun getItemCount(): Int = chatBotData.size
}