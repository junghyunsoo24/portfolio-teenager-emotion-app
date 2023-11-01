package com.example.portfolioteenageremotionpreventapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.portfolioteenageremotionpreventapp.R
import com.example.portfolioteenageremotionpreventapp.expertChat.ExpertChatDataPair

class ExpertChatAdapter(private val expertChatData: MutableList<ExpertChatDataPair>) :
    RecyclerView.Adapter<ExpertChatAdapter.MessageViewHolder>() {

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val inputMessageTextView: TextView = itemView.findViewById(R.id.inputChatTextView)
        val responseMessageTextView: TextView = itemView.findViewById(R.id.responseChatTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat, parent, false)
        return MessageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val messagePair = expertChatData[position]

        holder.inputMessageTextView.text = messagePair.inputMessage
        holder.inputMessageTextView.textSize = 15f

        holder.responseMessageTextView.text = messagePair.responseMessage
        holder.responseMessageTextView.textSize = 15f
    }

    override fun getItemCount(): Int = expertChatData.size
}