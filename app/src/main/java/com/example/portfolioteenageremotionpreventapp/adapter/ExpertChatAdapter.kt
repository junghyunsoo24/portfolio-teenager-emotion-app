package com.example.portfolioteenageremotionpreventapp.adapter

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.portfolioteenageremotionpreventapp.R
import com.example.portfolioteenageremotionpreventapp.appViewModel.AppViewModel
import com.example.portfolioteenageremotionpreventapp.expertChat.ExpertChatDataPair
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class ExpertChatAdapter(private val expertChatData: MutableList<ExpertChatDataPair>) :
    RecyclerView.Adapter<ExpertChatAdapter.MessageViewHolder>() {
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

    private fun checkOutPutDate(responseChatDataPair: ExpertChatDataPair): String? {
        if(responseChatDataPair.responseMessage !="") {
            val response = try {
                SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss",
                    Locale.getDefault()
                ).parse(responseChatDataPair.responseTime)
            } catch (e: ParseException) {
                convertToKoreanTime(responseChatDataPair.responseTime)
            }
            val responseSdf = SimpleDateFormat("yyyy년 M월 d일 (EEEE)", Locale.getDefault())
            return if (response != null) {
                responseSdf.format(response)
            } else {
                null
            }
        }
        return ""
    }

    private fun checkInputDate(inputChatDataPair: ExpertChatDataPair): String? {
        if(inputChatDataPair.inputMessage !="") {
            val input = try {
                SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss",
                    Locale.getDefault()
                ).parse(inputChatDataPair.inputTime)
            } catch (e: ParseException) {
                convertToKoreanTime(inputChatDataPair.inputTime)
            }
            val teenSdf = SimpleDateFormat("yyyy년 M월 d일 (EEEE)", Locale.getDefault())
            return if (input != null) {
                teenSdf.format(input)
            } else {
                null
            }
        }
        return ""
    }

    private fun convertToKoreanTime(timeString: String): Date? {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date = inputFormat.parse(timeString)

        val outputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        outputFormat.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        val koreanTime = outputFormat.format(date)

        return outputFormat.parse(koreanTime)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val messagePair = expertChatData[position]

        viewModel = AppViewModel.getInstance()

        holder.inputMessageTextView.textSize = 16f
        holder.responseMessageTextView.textSize = 16f
        holder.dateTextView.textSize = 16f
        holder.inputTimeView.textSize = 10f
        holder.outputTimeView.textSize = 10f

        if (messagePair.inputMessage.isEmpty()) {
            holder.inputMessageTextView.visibility = View.GONE
            holder.inputTimeView.visibility = View.GONE
        } else {
            holder.inputMessageTextView.text = messagePair.inputMessage
        }

        if (messagePair.responseMessage.isEmpty()) {
            holder.responseMessageTextView.visibility = View.GONE
            holder.outputTimeView.visibility = View.GONE
        } else {
            holder.responseMessageTextView.text = messagePair.responseMessage
        }

        val currentDate: LocalDate = LocalDate.now()
        val formatter: DateTimeFormatter =
            DateTimeFormatter.ofPattern("yyyy년 M월 d일 (EEEE)", Locale.KOREAN)
        val formattedDate: String = currentDate.format(formatter)

        var input: Date? = null
        if (!messagePair.inputMessage.isNullOrEmpty()) {
            input = try {
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(messagePair.inputTime)
            } catch (e: ParseException) {
                convertToKoreanTime(messagePair.inputTime)
            }
        }
        val inputSdf = SimpleDateFormat("yyyy년 M월 d일 (EEEE)", Locale.getDefault())
        val inputDate: String? = if (input != null) {
            inputSdf.format(input)
        } else {
            null
        }
        val inputTimeSdf = SimpleDateFormat("a hh:mm", Locale.getDefault())
        val inputTime: String? = if (input != null) {
            inputTimeSdf.format(input)
        } else {
            null
        }

        var response: Date? = null
        if (messagePair.responseMessage.isNotEmpty()) {
            response = convertToKoreanTime(messagePair.responseTime)
        }
        val responseSdf = SimpleDateFormat("yyyy년 M월 d일 (EEEE)", Locale.getDefault())
        val responseDate: String? = if (response != null) {
            responseSdf.format(response)
        } else {
            null
        }
        val responseTimeSdf = SimpleDateFormat("a hh:mm", Locale.getDefault())
        val responseTime: String? = if (response != null) {
            responseTimeSdf.format(response)
        } else {
            null
        }

//        if (position == 0 || checkInputDate(expertChatData[position - 1]) != inputDate  && checkInputDate(expertChatData[position - 1]) != "") {
//            holder.dateTextView.text = inputDate
//        } else if (position == 0 || checkOutPutDate(expertChatData[position - 1]) != responseDate && checkOutPutDate(expertChatData[position - 1]) != ""){
//            holder.dateTextView.text = responseDate
//        }else{
//            holder.dateTextView.text = null
//        }

        if (expertChatData != null) {
            if (viewModel.getUserId().value == messagePair.id) {
                holder.inputMessageTextView.text = messagePair.inputMessage
                holder.inputTimeView.text = inputTime
//                if (inputDate != null) {
//                    Log.e("input", inputDate)
//                }
                if(position == 0 || inputDate!="" &&
                    (checkOutPutDate(expertChatData[position - 1]) != "" && checkOutPutDate(expertChatData[position - 1]) != inputDate) ||
                    (checkInputDate(expertChatData[position - 1]) != "" && checkInputDate(expertChatData[position - 1]) != inputDate)) {
                    holder.dateTextView.text = inputDate
                    if (inputDate != null) {
                        Log.e("inputDate", inputDate)
                    }
                    checkInputDate(expertChatData[position - 1])?.let { Log.e("position-1", it) }

                }

            }
            if (viewModel.getUserId().value != messagePair.id) {
                holder.responseMessageTextView.text = messagePair.responseMessage
                holder.outputTimeView.text = responseTime
//                if (responseDate != null) {
//                    Log.e("output", responseDate)
//                }
                if(position == 0 || responseDate!="" &&
                    (checkOutPutDate(expertChatData[position - 1]) != "" && checkOutPutDate(expertChatData[position - 1]) != responseDate) ||
                    (checkInputDate(expertChatData[position - 1]) != "" && checkInputDate(expertChatData[position - 1]) != responseDate)) {
                    holder.dateTextView.text = responseDate
                }
            }


        }

    }

    override fun getItemCount(): Int = expertChatData.size
}