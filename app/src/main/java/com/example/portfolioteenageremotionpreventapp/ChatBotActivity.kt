package com.example.portfolioteenageremotionpreventapp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.portfolioteenageremotionpreventapp.adapter.ChatBotAdapter
import com.example.portfolioteenageremotionpreventapp.appViewModel.AppViewModel
import com.example.portfolioteenageremotionpreventapp.chatbot.ChatBotApi
import com.example.portfolioteenageremotionpreventapp.chatbot.ChatBotData
import com.example.portfolioteenageremotionpreventapp.chatbot.ChatBotDataPair
import com.example.portfolioteenageremotionpreventapp.databinding.ActivityChatbotBinding
import com.example.portfolioteenageremotionpreventapp.infoChatbot.InfoChatBotApi
import com.example.portfolioteenageremotionpreventapp.infoChatbot.InfoChatBotData
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ChatBotActivity : AppCompatActivity() {
    private lateinit var teenMessage: String
    private lateinit var id: String

    private lateinit var adapter: ChatBotAdapter
    private val messages = mutableListOf<ChatBotDataPair>()
    private lateinit var binding: ActivityChatbotBinding

    private lateinit var viewModel: AppViewModel

//    private val chatBotKey = "chatBot_history"

//    private lateinit var currentDate: String
    private lateinit var currentDateTime: LocalDateTime
    private lateinit var formatter: DateTimeFormatter
    private lateinit var formattedDateTime: String

    private lateinit var startDate: String
    private lateinit var endDate: String

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatbotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = AppViewModel.getInstance()

        id = viewModel.getUserId().value.toString()

//        viewModel.setCurrentDate(getCurrentDate())
//        binding.chatBot.text = viewModel.getCurrentDate().value

        currentDateTime = LocalDateTime.now()
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        formattedDateTime = currentDateTime.format(formatter)


        adapter = ChatBotAdapter(messages)
        binding.chatBotRecyclerView.adapter = adapter
        binding.chatBotRecyclerView.layoutManager = LinearLayoutManager(this)


        val currentCnt = viewModel.getCnt().value

        mobileToServers()

        binding.input.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                teenMessage = binding.input.text.toString()
//                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                inputMethodManager.hideSoftInputFromWindow(binding.input.windowToken, 0)
                if (teenMessage.isNotBlank()) {
                    val chatBotDataPair = ChatBotDataPair(teenMessage, formattedDateTime, "","")
                    messages.add(chatBotDataPair)
                    adapter.notifyDataSetChanged()
                    scrollToBottom()
//                    saveChatHistory()

                    mobileToServer()

                    binding.input.text = null
                }
                true
            } else {
                false
            }
        }

        binding.chatDeliver.setOnClickListener {
            teenMessage = binding.input.text.toString()
            if (teenMessage.isNotBlank()) {
                val chatBotDataPair = ChatBotDataPair(teenMessage, formattedDateTime, "", "")
                messages.add(chatBotDataPair)
                adapter.notifyDataSetChanged()
                scrollToBottom()
//                saveChatHistory()

                mobileToServer()

                binding.input.text = null
            }
        }

        val actionBar: ActionBar? = supportActionBar

        actionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar?.setCustomView(R.layout.actionbar_all)

        val actionBarTitle = actionBar?.customView?.findViewById<TextView>(R.id.actionBarAll)
        actionBarTitle?.text = "AI 챗봇"

        actionBar?.setDisplayHomeAsUpEnabled(true)

        startDate = getLastWeekDate()
        endDate = getNextDayDate()

        val showCalendarButton: Button = findViewById(R.id.showCalendarButton)
        showCalendarButton.setOnClickListener {
            showCalendar()
        }

//        loadChatHistory()
    }

    private fun showCalendar() {
        val builder = MaterialDatePicker.Builder.dateRangePicker()
        val picker = builder.build()

        picker.addOnPositiveButtonClickListener { selection ->
            val startDateMillis = selection.first
            val endDateMillis = selection.second

            val startsDate = Date(startDateMillis)
            val endsDate = Date(endDateMillis)

            // Calendar 인스턴스 생성 및 시작 날짜 설정
            val startCalendar = Calendar.getInstance()
            startCalendar.time = startsDate

//            // 시작 날짜에 1일을 더함
//            startCalendar.add(Calendar.DAY_OF_MONTH, 1)

            // 종료 날짜에 1일을 더함
            val endCalendar = Calendar.getInstance()
            endCalendar.time = endsDate
            endCalendar.add(Calendar.DAY_OF_MONTH, 1)

            // 더한 날짜를 SimpleDateFormat을 사용하여 문자열로 변환
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            startDate = dateFormat.format(startCalendar.time)
            endDate = dateFormat.format(endCalendar.time)

//            mobileToServeres()
        }

        // 취소 버튼 클릭 리스너 설정
        picker.addOnNegativeButtonClickListener {
            // 캘린더 선택이 취소될 때의 동작 처리
        }

        picker.show(supportFragmentManager, picker.toString())
    }

    private fun mobileToServer() {
        lifecycleScope.launch {
            try {
                val message = ChatBotData(id, teenMessage)

                val response = viewModel.getUrl().value?.let {
                    // Retrofit 설정을 통해 타임아웃을 조정
                    val retrofit = ChatBotApi.retrofitService(it, timeout = 180) // 타임아웃을 60초로 설정
                    retrofit.sendMessage(message)
                }

                if (response != null) {
                    if (response.isSuccessful) {
                        val responseBody = response?.body()
                        if (responseBody != null) {
                            val responseData = responseBody.chatbot
                            val responseDate = responseBody.chatbotPreviousDate

                            val chatBotDataPair = ChatBotDataPair("", "",responseData, responseDate)
                            messages.add(chatBotDataPair)
                            adapter.notifyDataSetChanged()
                            scrollToBottom()
//                            saveChatHistory()
                        } else {
                            Log.e("@@@@Error3", "Response body is null")
                        }
                    } else {
                        Log.e("@@@@Error2", "Response not successful: ${response.code()}")
                    }
                }
            } catch (Ex: Exception) {
                Log.e("@@@@Error1", Ex.stackTraceToString())
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.myPage_btn -> {
                val intent = Intent(this, InfoListActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun mobileToServers() {
        lifecycleScope.launch {
            try {
                val message = InfoChatBotData(id)

                val response = viewModel.getUrl().value?.let {
                    // Retrofit 설정을 통해 타임아웃을 조정
                    val retrofit = InfoChatBotApi.retrofitService(it, timeout = 180) // 타임아웃을 60초로 설정
                    retrofit.sendMessage(message)
                }

                if (response != null) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            val responseDataList = responseBody.history

                            for (responseData in responseDataList) {
                                val chatBotDataPair = ChatBotDataPair(responseData.teen_message, responseData.teen_chat_date, responseData.chatbot_message, responseData.chatbot_chat_date)
                                messages.add(chatBotDataPair)
                            }

                            adapter.notifyDataSetChanged()
                            scrollToBottom()


//                            saveChatHistory()
                        } else {
                            Log.e("@@@@Error3", "Response body is null")
                        }
                    } else {
                        Log.e("@@@@Error2", "Response not successful: ${response.code()}")
                    }
                }
            } catch (Ex: Exception) {
                Log.e("@@@@Error1", Ex.stackTraceToString())
            }
        }
    }

    private fun scrollToBottom() {
        binding.chatBotRecyclerView.post {
            binding.chatBotRecyclerView.scrollToPosition(messages.size - 1)
        }
    }


//    private fun loadChatHistory() {
//        val chatBot = getSharedPreferences(chatBotKey, Context.MODE_PRIVATE)
//        val chatHistoryJson = chatBot.getString(id, "")
//
//        if (!chatHistoryJson.isNullOrEmpty()) {
//            val chatHistory = Gson().fromJson<List<ChatBotDataPair>>(
//                chatHistoryJson,
//                object : TypeToken<List<ChatBotDataPair>>() {}.type
//            )
//            messages.addAll(chatHistory)
//            adapter.notifyDataSetChanged()
//            scrollToBottom()
//        }
//    }
//
//    private fun saveChatHistory() {
//        val chatBot = getSharedPreferences(chatBotKey, Context.MODE_PRIVATE)
//        val editor = chatBot.edit()
//
//        val chatHistoryJson = Gson().toJson(messages)
//        editor.putString(id, chatHistoryJson)
//        editor.apply()
//    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일 EEEE", Locale.getDefault())
        val date = Date(System.currentTimeMillis())
        return dateFormat.format(date)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getLastWeekDate(): String {
        val currentDate = LocalDate.now()
        val lastWeekDate = currentDate.minusWeeks(1)

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())

        return lastWeekDate.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getNextDayDate(): String {
        val currentDate = LocalDate.now()
        val nextDayDate = currentDate.plusDays(1)

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())

        return nextDayDate.format(formatter)
    }

}


