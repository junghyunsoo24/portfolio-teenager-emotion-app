package com.example.portfolioteenageremotionpreventapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.portfolioteenageremotionpreventapp.R
import com.example.portfolioteenageremotionpreventapp.appViewModel.AppViewModel
import com.example.portfolioteenageremotionpreventapp.chatbot.ChatBotDataPair
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ChatBotAdapter(private val chatBotData: List<ChatBotDataPair>) : RecyclerView.Adapter<ChatBotAdapter.MessageViewHolder>() {
    private lateinit var viewModel: AppViewModel
    private var lastDate: String? = null
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

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        viewModel = AppViewModel.getInstance()

        val messagePair = chatBotData[position]

        holder.inputMessageTextView.textSize = 16f
        holder.responseMessageTextView.textSize = 16f
        holder.dateTextView.textSize = 16f
        holder.inputTimeView.textSize = 10f
        holder.outputTimeView.textSize = 10f

        if (messagePair.teenMessage.isNullOrEmpty()) {
            holder.inputMessageTextView.visibility = View.GONE
            holder.inputTimeView.visibility = View.GONE
            holder.dateTextView.visibility = View.GONE
        } else {
            holder.inputMessageTextView.text = messagePair.teenMessage

            holder.inputMessageTextView.visibility = View.VISIBLE
            holder.inputTimeView.visibility = View.VISIBLE
        }

        if (messagePair.chatBotMessage.isNullOrEmpty()) {
            holder.responseMessageTextView.visibility = View.GONE
            holder.outputTimeView.visibility = View.GONE
            holder.dateTextView.visibility = View.GONE
        } else {
            holder.responseMessageTextView.text = messagePair.chatBotMessage

            holder.responseMessageTextView.visibility = View.VISIBLE
            holder.outputTimeView.visibility = View.VISIBLE
            holder.dateTextView.visibility = View.VISIBLE
        }

//        val currentDate = Calendar.getInstance().time
//
//        val sdf = SimpleDateFormat("yyyy년 M월 d일 (EEEE)", Locale.getDefault())
//        val formattedDate = sdf.format(currentDate)
//
//        val sdfs = SimpleDateFormat("a hh:mm", Locale.getDefault())
//        val formattedDates = sdfs.format(currentDate)

        var chatBotDate: Date? = null

//        if (messagePair.chatBotMessageTime != null) {
//            try {
//                // Try parsing using the first date format
//                chatBotDate = SimpleDateFormat("EEE MMM dd HH:mm:ss 'GMT'Z yyyy", Locale.US)
//                    .parse(messagePair.chatBotMessageTime)
//            } catch (e: ParseException) {
//                // If parsing fails, try the second date format
//                try {
//                    chatBotDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
//                        .parse(messagePair.chatBotMessageTime)
//                } catch (ex: ParseException) {
//                    ex.printStackTrace()
//                    // Handle the case when both parsing attempts fail
//                }
//            }
//        }

        if (!messagePair.chatBotMessageTime.isNullOrEmpty()) {
            chatBotDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).parse(messagePair.chatBotMessageTime)
        }

        val chatBotSdf = SimpleDateFormat("yyyy년 M월 d일 (EEEE)", Locale.getDefault())
        val chatBotFormattedDate: String? = if (chatBotDate != null) {
            chatBotSdf.format(chatBotDate)
        } else {
            null
        }

        val chatBotTimeSdf = SimpleDateFormat("a hh:mm", Locale.getDefault())
        val chatBotFormattedDates: String? = if (chatBotDate != null) {
            chatBotTimeSdf.format(chatBotDate)
        } else {
            null
        }

        if(chatBotData.isEmpty()){
            holder.dateTextView.text = chatBotFormattedDate
            lastDate = chatBotFormattedDate
        }


        if (chatBotDate != null) {
            if (messagePair.teenMessage.isNotEmpty() && messagePair.teenMessage.isNotEmpty() && messagePair.chatBotMessage.isNotEmpty() && messagePair.chatBotMessageTime.isNotEmpty()) {
                if (chatBotDate != null) {
                        holder.inputMessageTextView.text = messagePair.teenMessage
                        holder.responseMessageTextView.text = messagePair.chatBotMessage
                        holder.inputTimeView.text = chatBotFormattedDates
                        holder.outputTimeView.text = chatBotFormattedDates

                        // 날짜가 변경되었을 때만 표시
                        if (chatBotFormattedDate != lastDate) {
                            holder.dateTextView.text = chatBotFormattedDate
                            lastDate = chatBotFormattedDate
                        } else {
                            holder.dateTextView.visibility = View.GONE
                        }
                }
            }
        }
    }

    override fun getItemCount(): Int = chatBotData.size
}