package com.example.portfolioteenageremotionpreventapp

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.portfolioteenageremotionpreventapp.adapter.ExpertChatAdapter
import com.example.portfolioteenageremotionpreventapp.appViewModel.AppViewModel
import com.example.portfolioteenageremotionpreventapp.databinding.ActivityExpertchatBinding
import com.example.portfolioteenageremotionpreventapp.expertChat.ExpertChatDataPair
import com.example.portfolioteenageremotionpreventapp.infoTeenChat.InfoTeenChatApi
import com.example.portfolioteenageremotionpreventapp.infoTeenChat.InfoTeenChatData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.URISyntaxException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ExpertChatActivity : AppCompatActivity() {
    private lateinit var input: String
    private lateinit var id: String

    private lateinit var adapter: ExpertChatAdapter
    private val messages = mutableListOf<ExpertChatDataPair>()
    private lateinit var binding: ActivityExpertchatBinding

    private lateinit var viewModel: AppViewModel

    private lateinit var startDate: String
    private lateinit var endDate: String

//    private val expertKey = "expert_history"

    private lateinit var mSocket: Socket

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExpertchatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = AppViewModel.getInstance()

        id = viewModel.getUserId().value.toString()

//        viewModel.setCurrentDate(getCurrentDate())
//        binding.expertChat.text = viewModel.getCurrentDate().value

        adapter = ExpertChatAdapter(messages)
        binding.expertChatRecyclerView.adapter = adapter
        binding.expertChatRecyclerView.layoutManager = LinearLayoutManager(this)

        mobileToServers()

        try {
            val options = IO.Options()
            options.forceNew = true
            mSocket = IO.socket(resources.getString(R.string.chat_ip_server),options)
            mSocket.connect()

            val roomName = id
            //(1)입장
            val data = JoinData(id, roomName)
            val jsonObject = JSONObject()
            jsonObject.put("id", data.id)
            jsonObject.put("room", data.room)
            mSocket.emit("join", jsonObject)

            //(3)메시지 수신
            mSocket.on("roomMessage") { args ->
                val roomMessage  = args[0] as String
                val senderID = args[1] as String

                val currentDateTime = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                val formattedDateTime = currentDateTime.format(formatter)
                if (senderID != id) {
                    runOnUiThread {
                        val messagePair = ExpertChatDataPair("", "", roomMessage, formattedDateTime, senderID)
                        messages.add(messagePair)

                        adapter.notifyDataSetChanged()
//                        saveExpertChatHistory()
                        scrollToBottom()
                    }
                }
            }

            binding.input.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    input = binding.input.text.toString()
//                    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                    inputMethodManager.hideSoftInputFromWindow(binding.input.windowToken, 0)
                    val currentDateTime = LocalDateTime.now()
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                    val formattedDateTime = currentDateTime.format(formatter)
                    if (input.isNotBlank()) {
                        val messagePair = ExpertChatDataPair(input, formattedDateTime, "", "", id)
                        messages.add(messagePair)
                        adapter.notifyDataSetChanged()
//                        saveExpertChatHistory()
                        scrollToBottom()
                        //(2)메시지 전달
                        val message = input
                        val dataToJson2 = roomName?.let{ SocketData(message, it, id) }
                        val jsonObject2 = JSONObject()
                        if (dataToJson2 != null)
                            jsonObject2.put("message", dataToJson2.message)
                        if (dataToJson2 != null)
                            jsonObject2.put("room", dataToJson2.room)
                        if (dataToJson2 != null)
                            jsonObject2.put("senderID", dataToJson2.senderID)
                        mSocket.emit("chatMessage", jsonObject2)
//                        showAlertDialog(message)
                        binding.input.text = null
                    }
                    true
                } else {
                    false
                }
            }

            binding.chatDeliver.setOnClickListener {
                input = binding.input.text.toString()
                if (input.isNotBlank()) {
                    val currentDateTime = LocalDateTime.now()
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                    val formattedDateTime = currentDateTime.format(formatter)
                    val messagePair = ExpertChatDataPair(input, formattedDateTime, "", "", id)
                    messages.add(messagePair)
                    adapter.notifyDataSetChanged()
//                    saveExpertChatHistory()
                    scrollToBottom()
                    //(2)메시지 전달
                    val message = input
                    val dataToJson2 = roomName?.let{ SocketData(message, it, id) }
                    val jsonObject2 = JSONObject()
                    if (dataToJson2 != null)
                        jsonObject2.put("message", dataToJson2.message)
                    if (dataToJson2 != null)
                        jsonObject2.put("room", dataToJson2.room)
                    if (dataToJson2 != null)
                        jsonObject2.put("senderID", dataToJson2.senderID)
                    mSocket.emit("chatMessage", jsonObject2)
//                        showAlertDialog(message)
                    binding.input.text = null
                }
                true
            }
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }

        val actionBar: ActionBar? = supportActionBar

        actionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar?.setCustomView(R.layout.actionbar_all)

        val actionBarTitle = actionBar?.customView?.findViewById<TextView>(R.id.actionBarAll)
        actionBarTitle?.text = "전문가 채팅"

        actionBar?.setDisplayHomeAsUpEnabled(true)

//        loadChatHistory()
    }

//    private fun showAlertDialog(message: String) {
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle("채팅 보내기 성공")
//        builder.setMessage("$message\n를 성공적으로 보냈습니다. 답장올때까지 기다려주세요.")
//        builder.setPositiveButton("확인") { dialog, _ ->
//            dialog.dismiss()
//        }
//        builder.show()
//    }

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

    private fun scrollToBottom() {
        binding.expertChatRecyclerView.post {
            binding.expertChatRecyclerView.scrollToPosition(messages.size - 1)
        }
    }

//    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
//        val imm: InputMethodManager =
//            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
//        return super.dispatchTouchEvent(ev)
//    }

//    private fun loadChatHistory() {
//        val expert = getSharedPreferences(expertKey, Context.MODE_PRIVATE)
//        val chatHistoryJson = expert.getString(id, "")
//
//        if (!chatHistoryJson.isNullOrEmpty()) {
//            val chatHistory = Gson().fromJson<List<ExpertChatDataPair>>(chatHistoryJson, object : TypeToken<List<ExpertChatDataPair>>() {}.type)
//            messages.addAll(chatHistory)
//            adapter.notifyDataSetChanged()
//            scrollToBottom()
//        }
//    }
//
//    private fun saveExpertChatHistory() {
//        val expert = getSharedPreferences(expertKey, Context.MODE_PRIVATE)
//        val editor = expert.edit()
//
//        val chatHistoryJson = Gson().toJson(messages)
//        editor.putString(id, chatHistoryJson)
//        editor.apply()
//    }

    override fun onDestroy() {
        super.onDestroy()
        mSocket.disconnect()
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일 EEEE", Locale.getDefault())
        val date = Date(System.currentTimeMillis())
        return dateFormat.format(date)
    }

    private fun mobileToServers() {
        lifecycleScope.launch {
            try {
                val message = InfoTeenChatData(id)

                val response = viewModel.getUrl().value?.let {
                    // Retrofit 설정을 통해 타임아웃을 조정
                    val retrofit = InfoTeenChatApi.retrofitService(it, timeout = 180) // 타임아웃을 60초로 설정
                    retrofit.sendMessage(message)
                }

                if (response != null) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            val responseDataList = responseBody.history

                            for (responseData in responseDataList) {
                                if (responseData.from == id) {
                                    val teenChatDataPair = ExpertChatDataPair(responseData.sentence, responseData.date, "", "", responseData.from)
                                    messages.add(teenChatDataPair)
                                }
                                else if(responseData.from != id && responseData.room == id){
                                    val expertChatDataPair = ExpertChatDataPair("", "", responseData.sentence, responseData.date, responseData.from)
                                    messages.add(expertChatDataPair)
                                }
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

    data class SocketData(val message: String?, val room: String, val senderID: String)
    data class JoinData(val id: String, val room: String?)
}