package com.example.portfolioteenageremotionpreventapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.portfolioteenageremotionpreventapp.R
import com.example.portfolioteenageremotionpreventapp.chatbot.ChatBotDataPair
import java.text.SimpleDateFormat
import java.util.*

class ChatBotAdapter(private val chatBotData: List<ChatBotDataPair>) :
    RecyclerView.Adapter<ChatBotAdapter.MessageViewHolder>() {

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
        val messagePair = chatBotData[position]

        if (messagePair.inputMessage.isNullOrEmpty()) {
            holder.inputMessageTextView.visibility = View.GONE
            holder.inputTimeView.visibility = View.GONE
        } else {
            holder.inputMessageTextView.text = messagePair.inputMessage
            holder.inputMessageTextView.textSize = 16f
            holder.inputMessageTextView.visibility = View.VISIBLE

            holder.inputTimeView.text = "오후 5시:20"
            holder.inputTimeView.textSize = 10f
            holder.inputTimeView.visibility = View.VISIBLE
        }

        if (messagePair.responseMessage.isNullOrEmpty()) {
            holder.responseMessageTextView.visibility = View.GONE
            holder.outputTimeView.visibility = View.GONE
        } else {
            holder.responseMessageTextView.text = messagePair.responseMessage
            holder.responseMessageTextView.textSize = 16f
            holder.responseMessageTextView.visibility = View.VISIBLE

            holder.outputTimeView.text = "오후 5시:25"
            holder.outputTimeView.textSize = 10f
            holder.outputTimeView.visibility = View.VISIBLE
        }


        // 현재 날짜를 가져오기
        val currentDate = Calendar.getInstance().time

        // SimpleDateFormat을 사용하여 원하는 형식으로 날짜 포맷팅
        val sdf = SimpleDateFormat("yyyy년 M월 d일 (EEEE)", Locale.getDefault())
        val formattedDate = sdf.format(currentDate)

        // TextView에 형식화된 날짜 설정
        holder.dateTextView.text = formattedDate
    }

    override fun getItemCount(): Int = chatBotData.size
}