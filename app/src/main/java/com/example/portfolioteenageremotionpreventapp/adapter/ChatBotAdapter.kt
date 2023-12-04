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
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
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
        if(chatBotDataPair.chatBotMessage !="") {
            val bot = try {
                SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss",
                    Locale.getDefault()
                ).parse(chatBotDataPair.chatBotMessageTime)
            } catch (e: ParseException) {
                SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss",
                    Locale.getDefault()
                ).parse(chatBotDataPair.chatBotMessageTime)
            }
            val chatBotSdf = SimpleDateFormat("yyyy년 M월 d일 (EEEE)", Locale.getDefault())
            return if (bot != null) {
                chatBotSdf.format(bot)
            } else {
                null
            }
        }
            return ""
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

        if (messagePair.teenMessage.isEmpty()) {
            holder.inputMessageTextView.visibility = View.GONE
            holder.inputTimeView.visibility = View.GONE
        } else {
            holder.inputMessageTextView.text = messagePair.teenMessage
        }

        if (messagePair.chatBotMessage.isEmpty()) {
            holder.responseMessageTextView.visibility = View.GONE
            holder.outputTimeView.visibility = View.GONE
        } else {
            holder.responseMessageTextView.text = messagePair.chatBotMessage
        }

        val currentDate: LocalDate = LocalDate.now()
        val formatter: DateTimeFormatter =
            DateTimeFormatter.ofPattern("yyyy년 M월 d일 (EEEE)", Locale.KOREAN)
        val formattedDate: String = currentDate.format(formatter)

        var teen: Date? = null
        if (!messagePair.teenMessageTime.isNullOrEmpty()) {
            teen = try {
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(messagePair.teenMessageTime)
            } catch (e: ParseException) {
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).parse(messagePair.teenMessageTime)
            }
        }
        val teenSdf = SimpleDateFormat("yyyy년 M월 d일 (EEEE)", Locale.getDefault())
        val teenDate: String? = if (teen != null) {
            teenSdf.format(teen)
        } else {
            null
        }
        val teenTimeSdf = SimpleDateFormat("a hh:mm", Locale.getDefault())
        val teenTime: String? = if (teen != null) {
            teenTimeSdf.format(teen)
        } else {
            null
        }

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

        if (position == 0 || getFormattedDate(chatBotData[position - 1]) != botDate && getFormattedDate(chatBotData[position - 1]) != "") {
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

            if (messagePair.teenMessage.isNotEmpty() && messagePair.teenMessageTime.isNotEmpty() && messagePair.chatBotMessage.isEmpty() && messagePair.chatBotMessageTime.isEmpty()) {
                holder.inputMessageTextView.text = messagePair.teenMessage
                holder.inputTimeView.text = botTime
            }
            if (messagePair.teenMessage.isEmpty() && messagePair.teenMessageTime.isEmpty() && messagePair.chatBotMessage.isNotEmpty() && messagePair.chatBotMessageTime.isNotEmpty()) {
                holder.responseMessageTextView.text = messagePair.chatBotMessage
                holder.outputTimeView.text = botTime
            }


        }
    }

    override fun getItemCount(): Int = chatBotData.size
}