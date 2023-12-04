package com.example.portfolioteenageremotionpreventapp.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.portfolioteenageremotionpreventapp.R
import com.example.portfolioteenageremotionpreventapp.appViewModel.AppViewModel
import com.example.portfolioteenageremotionpreventapp.chatbot.ChatBotDataPair
import java.text.SimpleDateFormat
import java.util.*

class ChatBotAdapter(private val chatBotData: List<ChatBotDataPair>) : RecyclerView.Adapter<ChatBotAdapter.MessageViewHolder>() {
    private lateinit var viewModel: AppViewModel

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val inputMessageTextView: TextView = itemView.findViewById(R.id.inputChatTextView)
        val responseMessageTextView: TextView = itemView.findViewById(R.id.responseChatTextView)

        val dateTextView: TextView = itemView.findViewById(R.id.date)

        val inputTimeView: TextView = itemView.findViewById(R.id.input_time)
        val outputTimeView: TextView = itemView.findViewById(R.id.output_time)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat, parent, false)
        return MessageViewHolder(itemView)
    }

    private fun getFormattedDate(chatBotDataPair: ChatBotDataPair): String? {
        val bot = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).parse(chatBotDataPair.chatBotMessageTime)
        val chatBotSdf = SimpleDateFormat("yyyy년 M월 d일 (EEEE)", Locale.getDefault())
        return if (bot != null) {
            chatBotSdf.format(bot)
        } else {
            null
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        viewModel = AppViewModel.getInstance()

        val messagePair = chatBotData[position]

        holder.inputMessageTextView.textSize = 16f
        holder.responseMessageTextView.textSize = 16f
        holder.dateTextView.textSize = 16f
        holder.inputTimeView.textSize = 10f
        holder.outputTimeView.textSize = 10f

        var bot: Date? = null
        if (messagePair.chatBotMessageTime.isNotEmpty()) {
            bot = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).parse(messagePair.chatBotMessageTime)
        }
        val chatBotSdf = SimpleDateFormat("yyyy년 M월 d일 (EEEE)", Locale.getDefault())
        val botDate: String? = if (bot != null) {
            chatBotSdf.format(bot)
        } else {
            null
        }
        val chatBotTimeSdf = SimpleDateFormat("a hh:mm", Locale.getDefault())
        val botTime: String? = if (bot != null) {
            chatBotTimeSdf.format(bot)
        } else {
            null
        }

        if (position == 0 || getFormattedDate(chatBotData[position - 1]) != botDate) {
            holder.dateTextView.text = botDate
        } else {
            holder.dateTextView.text = null
        }

        if (chatBotData != null) {
            if (messagePair.teenMessage.isNotEmpty() && messagePair.teenMessageTime.isNotEmpty() && messagePair.chatBotMessage.isNotEmpty() && messagePair.chatBotMessageTime.isNotEmpty()) {
                if (bot != null) {
                    holder.inputMessageTextView.text = messagePair.teenMessage
                    holder.responseMessageTextView.text = messagePair.chatBotMessage
                    holder.inputTimeView.text = botTime
                    holder.outputTimeView.text = botTime
                }
            }
        }
    }

    override fun getItemCount(): Int = chatBotData.size
}