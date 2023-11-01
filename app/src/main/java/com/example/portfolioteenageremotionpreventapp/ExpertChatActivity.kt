package com.example.portfolioteenageremotionpreventapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.portfolioteenageremotionpreventapp.adapter.ExpertChatAdapter
import com.example.portfolioteenageremotionpreventapp.appViewModel.AppViewModel
import com.example.portfolioteenageremotionpreventapp.databinding.ActivityExpertchatBinding
import com.example.portfolioteenageremotionpreventapp.expertChat.ExpertChatDataPair
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import java.net.URISyntaxException

class ExpertChatActivity : AppCompatActivity() {
    private lateinit var input: String
    private lateinit var id: String

    private lateinit var adapter: ExpertChatAdapter
    private val messages = mutableListOf<ExpertChatDataPair>()
    private lateinit var binding: ActivityExpertchatBinding

    private lateinit var viewModel: AppViewModel

    private val expertKey = "expert_history"

    private lateinit var mSocket: Socket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExpertchatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = AppViewModel.getInstance()

        id = viewModel.getUserId().value!!

        adapter = ExpertChatAdapter(messages)
        binding.expertChatRecyclerView.adapter = adapter
        binding.expertChatRecyclerView.layoutManager = LinearLayoutManager(this)

        try {
            val options = IO.Options()
            options.forceNew = true
            mSocket = IO.socket(resources.getString(R.string.chat_ip_server),options)
            mSocket.connect()

            val roomName = id
            Log.e("test", roomName)
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

                if (senderID != id) {
                    runOnUiThread {
                        val messagePair = ExpertChatDataPair(input, roomMessage)
                        messages.add(messagePair)

                        adapter.notifyDataSetChanged()
                        saveExpertChatHistory()
                        scrollToBottom()
                    }
                }
            }

            binding.input.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    input = binding.input.text.toString()
                    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(binding.input.windowToken, 0)
                    if (input.isNotBlank()) {
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

                        showAlertDialog(message)

                        binding.input.text = null
                    }
                    true
                } else {
                    false
                }
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

        loadChatHistory()
    }

    private fun showAlertDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("채팅 보내기 성공")
        builder.setMessage("$message\n를 성공적으로 보냈습니다. 답장올때까지 기다려주세요.")
        builder.setPositiveButton("확인") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.myPage_btn -> {
                val intent = Intent(this, MyPageActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun scrollToBottom() {
        binding.expertChatRecyclerView.post {
            binding.expertChatRecyclerView.smoothScrollToPosition(adapter.itemCount - 1)
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val imm: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return super.dispatchTouchEvent(ev)
    }

    private fun loadChatHistory() {
        val expert = getSharedPreferences(expertKey, Context.MODE_PRIVATE)
        val chatHistoryJson = expert.getString(id, "")

        if (!chatHistoryJson.isNullOrEmpty()) {
            val chatHistory = Gson().fromJson<List<ExpertChatDataPair>>(chatHistoryJson, object : TypeToken<List<ExpertChatDataPair>>() {}.type)
            messages.addAll(chatHistory)
            adapter.notifyDataSetChanged()
            scrollToBottom()
        }
    }

    private fun saveExpertChatHistory() {
        val expert = getSharedPreferences(expertKey, Context.MODE_PRIVATE)
        val editor = expert.edit()

        val chatHistoryJson = Gson().toJson(messages)
        editor.putString(id, chatHistoryJson)
        editor.apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        mSocket.disconnect()
    }

    data class SocketData(val message: String?, val room: String, val senderID: String)
    data class JoinData(val id: String, val room: String?)
}